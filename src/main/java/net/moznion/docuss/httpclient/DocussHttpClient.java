package net.moznion.docuss.httpclient;

import java.net.URI;

import org.apache.http.HttpEntity;

public interface DocussHttpClient<T> {
    RequestExecutor<T> get(URI uri);

    RequestExecutor<T> post(URI uri, HttpEntity body);

    RequestExecutor<T> put(URI uri, HttpEntity body);

    RequestExecutor<T> delete(URI uri);
}
