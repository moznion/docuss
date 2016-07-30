package net.moznion.docuss.httpclient;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

import net.moznion.docuss.DocussDocument;

public interface DocussHttpClient<T, U> {
    Function<Consumer<T>, DocussDocument> get(URI uri);

    Function<Consumer<T>, DocussDocument> post(URI uri, U body);

    Function<Consumer<T>, DocussDocument> put(URI uri, U body);

    Function<Consumer<T>, DocussDocument> delete(URI uri);
}
