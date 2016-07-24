package net.moznion.docuss.presenter;

import net.moznion.docuss.DocussResponse;
import net.moznion.docuss.formatter.DocussFormatterGenerator;

public class StandardOutDocussPresenter implements DocussPresenter {
    @Override
    public void out(DocussFormatterGenerator formatter, DocussResponse response) {
        System.out.println(formatter.getFormatterGenerator().apply(response));
    }
}
