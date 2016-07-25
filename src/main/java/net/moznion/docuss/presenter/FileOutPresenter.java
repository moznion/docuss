package net.moznion.docuss.presenter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.moznion.docuss.DocussResponse;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

public class FileOutPresenter implements DocussPresenter {
    private final Path path;

    public FileOutPresenter(final String pathStr) {
        path = Paths.get(pathStr);
    }

    @Override
    public void out(DocussFormatterGenerator formatter, DocussResponse response) {
        try {
            Files.write(path,
                        formatter.getFormatterGenerator().apply(response).getBytes(),
                        StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
