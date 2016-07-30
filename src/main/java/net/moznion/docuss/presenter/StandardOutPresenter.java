package net.moznion.docuss.presenter;

import net.moznion.docuss.DocussDocument;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

/**
 * A presenter with STDOUT.
 */
public class StandardOutPresenter implements DocussPresenter {
    /**
     * Output formatted document to STDOUT.
     *
     * @param formatterGenerator Formatter generator for the result document.
     * @param document           The document of result.
     */
    @Override
    public void out(DocussFormatterGenerator formatterGenerator, DocussDocument document) {
        System.out.print(formatterGenerator.getFormatterGenerator().apply(document));
    }
}
