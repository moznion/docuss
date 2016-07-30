package net.moznion.docuss.presenter;

import net.moznion.docuss.DocussDocument;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * A presenter with file I/O.
 */
public class FileOutPresenter implements DocussPresenter {
    private final Path path;

    /**
     * A constructor.
     *
     * @param path File to output the result.
     */
    public FileOutPresenter(final Path path) {
        this.path = path;
    }

    /**
     * Append formatted document to file.
     *
     * @param formatterGenerator Formatter generator for the result document.
     * @param document           The document of result.
     */
    @Override
    public void out(DocussFormatterGenerator formatterGenerator, DocussDocument document) {
        try {
            Files.write(path,
                    formatterGenerator.getFormatterGenerator().apply(document).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
