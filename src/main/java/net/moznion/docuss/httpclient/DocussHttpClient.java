package net.moznion.docuss.httpclient;

import net.moznion.docuss.DocussDocument;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An interface for HTTP client.
 *
 * @param <B> A class that represents HTTP body.
 * @param <R> A class that represents HTTP response.
 */
public interface DocussHttpClient<B, R> {
    /**
     * Request by HTTP Get method.
     *
     * @param uri URI of request.
     * @return Function that takes consumer to test response and returns document of the result.
     */
    Function<Consumer<R>, DocussDocument> get(URI uri);

    /**
     * Request by HTTP Post method.
     *
     * @param uri  URI of request.
     * @param body Body of request.
     * @return Function that takes consumer to test response and returns document of the result.
     */
    Function<Consumer<R>, DocussDocument> post(URI uri, B body);

    /**
     * Request by HTTP Put method.
     *
     * @param uri  URI of request.
     * @param body Body of request.
     * @return Function that takes consumer to test response and returns document of the result.
     */
    Function<Consumer<R>, DocussDocument> put(URI uri, B body);

    /**
     * Request by HTTP Delete method.
     *
     * @param uri URI of request.
     * @return Function that takes consumer to test response and returns document of the result.
     */
    Function<Consumer<R>, DocussDocument> delete(URI uri);
}
