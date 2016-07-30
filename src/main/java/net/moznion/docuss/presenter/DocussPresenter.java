package net.moznion.docuss.presenter;

import net.moznion.docuss.DocussDocument;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

/**
 * An interface of method for outputting document.
 */
public interface DocussPresenter {
    /**
     * Output formatted document.
     *
     * @param formatterGenerator Formatter generator for the result document.
     * @param document           The document of result.
     */
    void out(DocussFormatterGenerator formatterGenerator, DocussDocument document);
}
