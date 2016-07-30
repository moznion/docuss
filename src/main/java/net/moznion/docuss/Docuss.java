package net.moznion.docuss;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.moznion.docuss.DocussDocument.Request;
import net.moznion.docuss.DocussDocument.Response;
import net.moznion.docuss.formatter.DocussFormatterGenerator;
import net.moznion.docuss.httpclient.ApacheHttpclient;
import net.moznion.docuss.httpclient.DocussHttpClient;
import net.moznion.docuss.httpclient.RequestExecutor;
import net.moznion.docuss.presenter.DocussPresenter;

public class Docuss<T extends HttpRequest, U extends HttpResponse> {
    private final HttpClientBuilder httpClientBuilder;
    private final DocussHttpClient<T, U> docussHttpClient;
    private final DocussFormatterGenerator formatterGenerator;
    private final DocussPresenter presenter;

    public Docuss(final DocussFormatterGenerator formatterGenerator,
                  final DocussPresenter presenter,
                  final DocussHttpClient<T, U> docussHttpClient) {
        httpClientBuilder = HttpClientBuilder.create();
        this.formatterGenerator = formatterGenerator;
        this.presenter = presenter;
        this.docussHttpClient = docussHttpClient;
    }

    public void shouldGet(final URI uri, final Consumer<U> expected) {
        shouldAny(uri, docussHttpClient.get(uri), Optional.empty(), expected);
    }

    public void shouldPost(final URI uri, final HttpEntity body, final Consumer<U> expected) {
        shouldAny(uri, docussHttpClient.post(uri, body), Optional.ofNullable(body), expected);
    }

    public void shouldPut(final URI uri, final HttpEntity body, final Consumer<U> expected) {
        shouldAny(uri, docussHttpClient.put(uri, body), Optional.ofNullable(body), expected);
    }

    public void shouldDelete(final URI uri, final Consumer<U> expected) {
        shouldAny(uri, docussHttpClient.delete(uri), Optional.empty(), expected);
    }

    private void shouldAny(final URI uri,
                           final RequestExecutor<T, U> requestExecutor,
                           final Optional<HttpEntity> httpEntityOptional,
                           final Consumer<U> expected) {
        final ApacheHttpclient apacheHttpclient = new ApacheHttpclient(httpClientBuilder);

        final Consumer<U> consumer = response -> {
            expected.accept(response);
            try {
                describe(uri, requestExecutor.getRequest(), response, httpEntityOptional);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        requestExecutor.getExecutor().accept(consumer);
    }

    private void describe(final URI uri,
                          final T request,
                          final U response,
                          final Optional<HttpEntity> httpEntityOptional) throws IOException {
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

        final DocussDocument docussDocument = new DocussDocument(req, res);
        presenter.out(formatterGenerator, docussDocument);
    }

    private static List<String> headerArrayToStrings(final Header[] headerArray) {
        final List<String> headers = new ArrayList<>();
        for (final Header header : headerArray) {
            headers.add(header.toString());
        }
        return headers;
    }
}
