package net.moznion.docuss;

import net.moznion.docuss.formatter.DocussFormatterGenerator;
import net.moznion.docuss.httpclient.DocussHttpClient;
import net.moznion.docuss.presenter.DocussPresenter;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A class to test with describing document.
 *
 * @param <B> A class that represents HTTP body.
 * @param <R> A class that represents HTTP response.
 */
public class Docuss<B, R> {
    private final DocussHttpClient<B, R> docussHttpClient;
    private final DocussFormatterGenerator formatterGenerator;
    private final DocussPresenter presenter;

    /**
     * A constructor.
     *
     * @param formatterGenerator Formatter generator for the result document.
     * @param presenter          Presenter to output the result.
     * @param docussHttpClient   HTTP client for testing.
     */
    public Docuss(final DocussFormatterGenerator formatterGenerator,
                  final DocussPresenter presenter,
                  final DocussHttpClient<B, R> docussHttpClient) {
        this.formatterGenerator = formatterGenerator;
        this.presenter = presenter;
        this.docussHttpClient = docussHttpClient;
    }

    /**
     * Test with HTTP Get method and output document.
     *
     * @param uri      URI of request.
     * @param expected Lambda to inspect response. HTTP response is given to this.
     */
    public void shouldGet(final URI uri, final Consumer<R> expected) {
        shouldAny(docussHttpClient.get(uri), expected);
    }

    /**
     * Test with HTTP Post method and output document.
     *
     * @param uri      URI of request.
     * @param body     Body of request.
     * @param expected Lambda to inspect response. HTTP response is given to this.
     */
    public void shouldPost(final URI uri, final B body, final Consumer<R> expected) {
        shouldAny(docussHttpClient.post(uri, body), expected);
    }

    /**
     * Test with HTTP Put method and output document.
     *
     * @param uri      URI of request.
     * @param body     Body of request.
     * @param expected Lambda to inspect response. HTTP response is given to this.
     */
    public void shouldPut(final URI uri, final B body, final Consumer<R> expected) {
        shouldAny(docussHttpClient.put(uri, body), expected);
    }

    /**
     * Test with HTTP Delete method and output document.
     *
     * @param uri      URI of request.
     * @param expected Lambda to inspect response. HTTP response is given to this.
     */
    public void shouldDelete(final URI uri, final Consumer<R> expected) {
        shouldAny(docussHttpClient.delete(uri), expected);
    }

    private void shouldAny(final Function<Consumer<R>, DocussDocument> requestExecutor,
                           final Consumer<R> expected) {
        presenter.out(formatterGenerator, requestExecutor.apply(expected));
    }
}
