package net.moznion.docuss.presenter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import net.moznion.docuss.DocussDocument;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

public class FileOutPresenter implements DocussPresenter {
    private final Path path;

    public FileOutPresenter(final Path path) {
        this.path = path;
    }

    @Override
    public void out(DocussFormatterGenerator formatterGenerator, DocussDocument response) {
        try {
            Files.write(path,
                        formatterGenerator.getFormatterGenerator().apply(response).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
