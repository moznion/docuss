package net.moznion.docuss.httpclient;

import java.net.URI;

import org.apache.http.HttpEntity;

public interface DocussHttpClient<T, U> {
    RequestExecutor<T, U> get(URI uri);

    RequestExecutor<T, U> post(URI uri, HttpEntity body);

    RequestExecutor<T, U> put(URI uri, HttpEntity body);

    RequestExecutor<T, U> delete(URI uri);
}
