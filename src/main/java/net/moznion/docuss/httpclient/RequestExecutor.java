package net.moznion.docuss.httpclient;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestExecutor<T, U> {
    private final T request;
    private final Consumer<Consumer<U>> executor;
}
