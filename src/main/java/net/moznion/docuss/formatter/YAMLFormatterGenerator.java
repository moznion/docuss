package net.moznion.docuss.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.moznion.docuss.DocussDocument;

import java.util.function.Function;

/**
 * An generator of formatter that formats as YAML.
 */
public class YAMLFormatterGenerator implements DocussFormatterGenerator {
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    /**
     * Return generator of formatter that formats as YAML.
     *
     * @return Generator of formatter that formats as YAML.
     */
    @Override
    public Function<DocussDocument, String> getFormatterGenerator() {
        return docussResponse -> {
            try {
                return objectMapper.writeValueAsString(docussResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
