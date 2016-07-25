package net.moznion.docuss.presenter;

import net.moznion.docuss.DocussDocument;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

public interface DocussPresenter {
    void out(DocussFormatterGenerator formatterGenerator, DocussDocument response);
}
