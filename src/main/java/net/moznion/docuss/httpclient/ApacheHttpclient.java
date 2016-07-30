package net.moznion.docuss.httpclient;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.moznion.docuss.DocussDocument;
import net.moznion.docuss.DocussDocument.Request;
import net.moznion.docuss.DocussDocument.Response;

public class ApacheHttpclient implements DocussHttpClient<HttpRequest, HttpResponse> {
    private final HttpClientBuilder httpClientBuilder;

    public ApacheHttpclient() {
        httpClientBuilder = HttpClientBuilder.create();
    }

    public ApacheHttpclient(final HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    @Override
    public RequestExecutor<HttpRequest, HttpResponse> get(final URI uri) {
        return requestAny(new HttpGet(uri), Optional.empty(), uri);
    }

    @Override
    public RequestExecutor<HttpRequest, HttpResponse> post(final URI uri, final HttpEntity body) {
        final HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(body);
        return requestAny(httpPost, Optional.ofNullable(body), uri);
    }

    @Override
    public RequestExecutor<HttpRequest, HttpResponse> put(final URI uri, final HttpEntity body) {
        final HttpPut httpPut = new HttpPut(uri);
        httpPut.setEntity(body);
        return requestAny(httpPut, Optional.ofNullable(body), uri);
    }

    @Override
    public RequestExecutor<HttpRequest, HttpResponse> delete(final URI uri) {
        return requestAny(new HttpDelete(uri), Optional.empty(), uri);
    }

    private RequestExecutor<HttpRequest, HttpResponse> requestAny(final HttpUriRequest request,
                                                                  final Optional<HttpEntity> httpEntityOptional,
                                                                  final URI uri) {
        return new RequestExecutor<>(request, consumer -> {
            try (final CloseableHttpClient httpClient = httpClientBuilder.build()) {
                try (final CloseableHttpResponse response = httpClient.execute(request)) {
                    consumer.accept(response);

                    final RequestLine requestLine = request.getRequestLine();
                    final String requestBody = httpEntityOptional.flatMap(entity -> {
                        try {
                            return Optional.ofNullable(EntityUtils.toString(entity));
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Optional.empty();
                        }
                    }).orElse(null);
                    final Request req = new Request(requestLine.getMethod(),
                                                    requestLine.getProtocolVersion().toString(),
                                                    uri.getPath(),
                                                    headerArrayToStrings(request.getAllHeaders()),
                                                    requestBody);

                    final StatusLine statusLine = response.getStatusLine();
                    final Response res = new Response(
                            statusLine.getProtocolVersion().toString(),
                            statusLine.getStatusCode(),
                            headerArrayToStrings(response.getAllHeaders()),
                            EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));

                    return new DocussDocument(req, res);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static List<String> headerArrayToStrings(final Header[] headerArray) {
        final List<String> headers = new ArrayList<>();
        for (final Header header : headerArray) {
            headers.add(header.toString());
        }
        return headers;
    }
}
