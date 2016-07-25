package net.moznion.docuss.formatter;

import net.moznion.docuss.DocussDocument;

import java.util.function.Function;

public interface DocussFormatterGenerator {
    Function<DocussDocument, String> getFormatterGenerator();
}
