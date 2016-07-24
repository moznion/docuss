package net.moznion.docuss.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.moznion.docuss.DocussResponse;

import java.util.function.Function;

public class YAMLDocussFormatterGenerator implements DocussFormatterGenerator {
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    @Override
    public Function<DocussResponse, String> getFormatterGenerator() {
        return (docussResponse) -> {
            try {
                return objectMapper.writeValueAsString(docussResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
