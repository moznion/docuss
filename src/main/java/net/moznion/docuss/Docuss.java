package net.moznion.docuss;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

import net.moznion.docuss.formatter.DocussFormatterGenerator;
import net.moznion.docuss.httpclient.DocussHttpClient;
import net.moznion.docuss.presenter.DocussPresenter;

public class Docuss<B, R> {
    private final DocussHttpClient<B, R> docussHttpClient;
    private final DocussFormatterGenerator formatterGenerator;
    private final DocussPresenter presenter;

    public Docuss(final DocussFormatterGenerator formatterGenerator,
                  final DocussPresenter presenter,
                  final DocussHttpClient<B, R> docussHttpClient) {
        this.formatterGenerator = formatterGenerator;
        this.presenter = presenter;
        this.docussHttpClient = docussHttpClient;
    }

    public void shouldGet(final URI uri, final Consumer<R> expected) {
        shouldAny(docussHttpClient.get(uri), expected);
    }

    public void shouldPost(final URI uri, final B body, final Consumer<R> expected) {
        shouldAny(docussHttpClient.post(uri, body), expected);
    }

    public void shouldPut(final URI uri, final B body, final Consumer<R> expected) {
        shouldAny(docussHttpClient.put(uri, body), expected);
    }

    public void shouldDelete(final URI uri, final Consumer<R> expected) {
        shouldAny(docussHttpClient.delete(uri), expected);
    }

    private void shouldAny(final Function<Consumer<R>, DocussDocument> requestExecutor,
                           final Consumer<R> expected) {
        presenter.out(formatterGenerator, requestExecutor.apply(expected));
    }
}
