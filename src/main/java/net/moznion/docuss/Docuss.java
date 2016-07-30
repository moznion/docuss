package net.moznion.docuss;

import java.net.URI;
import java.util.function.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import net.moznion.docuss.formatter.DocussFormatterGenerator;
import net.moznion.docuss.httpclient.DocussHttpClient;
import net.moznion.docuss.httpclient.RequestExecutor;
import net.moznion.docuss.presenter.DocussPresenter;

public class Docuss<T extends HttpRequest, U extends HttpResponse> {
    private final DocussHttpClient<T, U> docussHttpClient;
    private final DocussFormatterGenerator formatterGenerator;
    private final DocussPresenter presenter;

    public Docuss(final DocussFormatterGenerator formatterGenerator,
                  final DocussPresenter presenter,
                  final DocussHttpClient<T, U> docussHttpClient) {
        this.formatterGenerator = formatterGenerator;
        this.presenter = presenter;
        this.docussHttpClient = docussHttpClient;
    }

    public void shouldGet(final URI uri, final Consumer<U> expected) {
        shouldAny(docussHttpClient.get(uri), expected);
    }

    public void shouldPost(final URI uri, final HttpEntity body, final Consumer<U> expected) {
        shouldAny(docussHttpClient.post(uri, body), expected);
    }

    public void shouldPut(final URI uri, final HttpEntity body, final Consumer<U> expected) {
        shouldAny(docussHttpClient.put(uri, body), expected);
    }

    public void shouldDelete(final URI uri, final Consumer<U> expected) {
        shouldAny(docussHttpClient.delete(uri), expected);
    }

    private void shouldAny(final RequestExecutor<T, U> requestExecutor, final Consumer<U> expected) {
        presenter.out(formatterGenerator, requestExecutor.getExecutor().apply(expected));
    }
}
