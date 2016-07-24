package net.moznion.docuss.presenter;

import net.moznion.docuss.DocussResponse;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

public interface DocussPresenter {
    void out(DocussFormatterGenerator formatter, DocussResponse response);
}
