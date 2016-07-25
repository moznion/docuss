package net.moznion.docuss.presenter;

import net.moznion.docuss.DocussDocument;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

public class StandardOutPresenter implements DocussPresenter {
    @Override
    public void out(DocussFormatterGenerator formatterGenerator, DocussDocument response) {
        System.out.println(formatterGenerator.getFormatterGenerator().apply(response));
    }
}
