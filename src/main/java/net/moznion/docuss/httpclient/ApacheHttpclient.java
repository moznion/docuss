package net.moznion.docuss.httpclient;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ApacheHttpclient implements DocussHttpClient<HttpRequest, HttpResponse> {
    private final HttpClientBuilder httpClientBuilder;

    public ApacheHttpclient() {
        httpClientBuilder = HttpClientBuilder.create();
    }

    public ApacheHttpclient(final HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    @Override
    public RequestExecutor<HttpRequest, HttpResponse> get(final URI uri) {
        return requestAny(new HttpGet(uri));
    }

    @Override
    public RequestExecutor<HttpRequest, HttpResponse> post(final URI uri, final HttpEntity body) {
        final HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(body);
        return requestAny(httpPost);
    }

    @Override
    public RequestExecutor<HttpRequest, HttpResponse> put(final URI uri, final HttpEntity body) {
        final HttpPut httpPut = new HttpPut(uri);
        httpPut.setEntity(body);
        return requestAny(httpPut);
    }

    @Override
    public RequestExecutor<HttpRequest, HttpResponse> delete(final URI uri) {
        return requestAny(new HttpDelete(uri));
    }

    private RequestExecutor<HttpRequest, HttpResponse> requestAny(final HttpUriRequest req) {
        return new RequestExecutor<>(req, consumer -> {
            try (final CloseableHttpClient httpClient = httpClientBuilder.build()) {
                try (final CloseableHttpResponse response = httpClient.execute(req)) {
                    consumer.accept(response);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
