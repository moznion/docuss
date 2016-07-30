package net.moznion.docuss.formatter;

import net.moznion.docuss.DocussDocument;

import java.util.function.Function;

/**
 * An generator of formatter.
 */
public interface DocussFormatterGenerator {
    /**
     * Return formatter generator.
     *
     * @return Formatter generator for the result.
     */
    Function<DocussDocument, String> getFormatterGenerator();
}
