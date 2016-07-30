package net.moznion.docuss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represents HTTP request and response of document.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocussDocument {
    private Request request;
    private Response response;

    /**
     * A HTTP request of document.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String method;
        private String protocol;
        private String path;
        private List<String> headers;
        private String body;
        private Map<String, String> queryParameters;
    }

    /**
     * A HTTP response of document.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String protocol;
        private int statusCode;
        private List<String> headers;
        private String body;
    }
}
