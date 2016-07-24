package net.moznion.docuss.formatter;

import net.moznion.docuss.DocussResponse;

import java.util.function.Function;

public interface DocussFormatterGenerator {
    Function<DocussResponse, String> getFormatterGenerator();
}
