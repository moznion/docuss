package net.moznion.docuss;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
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

import net.moznion.docuss.formatter.DocussFormatterGenerator;
import net.moznion.docuss.presenter.DocussPresenter;

public class Docuss {
    private final HttpClientBuilder httpClientBuilder;
    private final DocussFormatterGenerator formatterGenerator;
    private final DocussPresenter presenter;

    public Docuss(final DocussFormatterGenerator formatterGenerator, final DocussPresenter presenter) {
        httpClientBuilder = HttpClientBuilder.create();
        this.formatterGenerator = formatterGenerator;
        this.presenter = presenter;
    }

    public void shouldGet(final URI uri, final Consumer<HttpResponse> expected) {
        shouldAny(uri, new HttpGet(uri), expected);
    }

    public void shouldPost(final URI uri, final Consumer<HttpResponse> expected) {
        shouldAny(uri, new HttpPost(uri), expected);
    }

    public void shouldPut(final URI uri, final Consumer<HttpResponse> expected) {
        shouldAny(uri, new HttpPut(uri), expected);
    }

    public void shouldDelete(final URI uri, final Consumer<HttpResponse> expected) {
        shouldAny(uri, new HttpDelete(uri), expected);
    }

    private void shouldAny(final URI uri, HttpUriRequest request, final Consumer<HttpResponse> expected) {
        try (final CloseableHttpClient httpClient = httpClientBuilder.build()) {
            try (final CloseableHttpResponse response = httpClient.execute(request)) {
                expected.accept(response);
                describe(uri, response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void describe(final URI uri, final HttpResponse response) throws IOException {
        final StatusLine statusLine = response.getStatusLine();
        final String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        final List<String> headers = new ArrayList<>();
        for (final Header header : response.getAllHeaders()) {
            headers.add(header.toString());
        }

        final DocussDocument docussDocument =
                new DocussDocument(uri.getPath(), statusLine.getStatusCode(), headers, body);
        presenter.out(formatterGenerator, docussDocument);
    }
}
