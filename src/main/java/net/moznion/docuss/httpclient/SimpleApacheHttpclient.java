package net.moznion.docuss.httpclient;

import net.moznion.docuss.DocussDocument;
import net.moznion.docuss.DocussDocument.Request;
import net.moznion.docuss.DocussDocument.Response;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A simple Docuss HTTP client that uses Apache httpclient.
 */
public class SimpleApacheHttpclient implements DocussHttpClient<HttpEntity, HttpResponse> {
    private final HttpClientBuilder httpClientBuilder;

    /**
     * A constructor that uses default {@link HttpClientBuilder}.
     */
    public SimpleApacheHttpclient() {
        httpClientBuilder = HttpClientBuilder.create();
    }

    /**
     * A constructor.
     *
     * @param httpClientBuilder Builder of Apache httpclient.
     */
    public SimpleApacheHttpclient(final HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    /**
     * Request by HTTP Get method.
     *
     * @param uri URI of request.
     * @return Function that takes consumer to test response and returns document of the result.
     */
    @Override
    public Function<Consumer<HttpResponse>, DocussDocument> get(final URI uri) {
        return requestAny(new HttpGet(uri), Optional.empty(), uri);
    }

    /**
     * Request by HTTP Post method.
     *
     * @param uri  URI of request.
     * @param body Body of request.
     * @return Function that takes consumer to test response and returns document of the result.
     */
    @Override
    public Function<Consumer<HttpResponse>, DocussDocument> post(final URI uri, final HttpEntity body) {
        final HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(body);
        return requestAny(httpPost, Optional.ofNullable(body), uri);
    }

    /**
     * Request by HTTP Put method.
     *
     * @param uri  URI of request.
     * @param body Body of request.
     * @return Function that takes consumer to test response and returns document of the result.
     */
    @Override
    public Function<Consumer<HttpResponse>, DocussDocument> put(final URI uri, final HttpEntity body) {
        final HttpPut httpPut = new HttpPut(uri);
        httpPut.setEntity(body);
        return requestAny(httpPut, Optional.ofNullable(body), uri);
    }

    /**
     * Request by HTTP Delete method.
     *
     * @param uri URI of request.
     * @return Function that takes consumer to test response and returns document of the result.
     */
    @Override
    public Function<Consumer<HttpResponse>, DocussDocument> delete(final URI uri) {
        return requestAny(new HttpDelete(uri), Optional.empty(), uri);
    }

    private Function<Consumer<HttpResponse>, DocussDocument> requestAny(final HttpUriRequest request,
                                                                        final Optional<HttpEntity> httpEntityOptional,
                                                                        final URI uri) {
        return expected -> {
            try (final CloseableHttpClient httpClient = httpClientBuilder.build()) {
                try (final CloseableHttpResponse response = httpClient.execute(request)) {
                    expected.accept(response);

                    final RequestLine requestLine = request.getRequestLine();
                    final String requestBody = httpEntityOptional.flatMap(entity -> {
                        try {
                            return Optional.ofNullable(EntityUtils.toString(entity));
                        } catch (IOException e) {
                            e.printStackTrace();
                            return Optional.empty();
                        }
                    }).orElse(null);

                    final List<NameValuePair> queryParams = new URIBuilder(uri).getQueryParams();

                    final Request req = new Request(requestLine.getMethod(),
                            requestLine.getProtocolVersion().toString(),
                            uri.getPath(),
                            headerArrayToStrings(request.getAllHeaders()),
                            requestBody,
                            queryParamsToMap(queryParams));

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
        };
    }

    private static List<String> headerArrayToStrings(final Header[] headerArray) {
        final List<String> headers = new ArrayList<>();
        for (final Header header : headerArray) {
            headers.add(header.toString());
        }
        return headers;
    }

    private static Map<String, String> queryParamsToMap(final List<NameValuePair> queryParams) {
        final Map<String, String> queryParamsMap = new HashMap<>();
        for (NameValuePair queryParam : queryParams) {
            queryParamsMap.put(queryParam.getName(), queryParam.getValue());
        }
        return queryParamsMap;
    }
}
