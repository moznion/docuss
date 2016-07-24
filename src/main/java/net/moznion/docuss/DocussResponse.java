package net.moznion.docuss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
class DocussResponse {
    private String path;
    private int statusCode;
    private List<String> headers;
    private String body;
}
