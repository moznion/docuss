package net.moznion.docuss;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

import net.moznion.docuss.formatter.DocussFormatterGenerator;
import net.moznion.docuss.httpclient.DocussHttpClient;
import net.moznion.docuss.presenter.DocussPresenter;

public class Docuss<T, U> {
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

    public void shouldGet(final URI uri, final Consumer<T> expected) {
        shouldAny(docussHttpClient.get(uri), expected);
    }

    public void shouldPost(final URI uri, final U body, final Consumer<T> expected) {
        shouldAny(docussHttpClient.post(uri, body), expected);
    }

    public void shouldPut(final URI uri, final U body, final Consumer<T> expected) {
        shouldAny(docussHttpClient.put(uri, body), expected);
    }

    public void shouldDelete(final URI uri, final Consumer<T> expected) {
        shouldAny(docussHttpClient.delete(uri), expected);
    }

    private void shouldAny(final Function<Consumer<T>, DocussDocument> requestExecutor,
                           final Consumer<T> expected) {
        presenter.out(formatterGenerator, requestExecutor.apply(expected));
    }
}
