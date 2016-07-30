package net.moznion.docuss.httpclient;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

import net.moznion.docuss.DocussDocument;

public interface DocussHttpClient<B, R> {
    Function<Consumer<R>, DocussDocument> get(URI uri);

    Function<Consumer<R>, DocussDocument> post(URI uri, B body);

    Function<Consumer<R>, DocussDocument> put(URI uri, B body);

    Function<Consumer<R>, DocussDocument> delete(URI uri);
}
