package net.moznion.docuss.httpclient;

import java.util.function.Consumer;
import java.util.function.Function;

import net.moznion.docuss.DocussDocument;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestExecutor<T, U> {
    private final T request;
    private final Function<Consumer<U>, DocussDocument> executor;
}
