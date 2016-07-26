package net.moznion.docuss;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocussDocument {
    private Request request;
    private Response response;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String method;
        private String protocol;
        private String path;
        private List<String> headers;
        private String body;
    }

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
