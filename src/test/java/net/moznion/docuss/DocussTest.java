package net.moznion.docuss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import net.moznion.capture.output.stream.Capturer;
import net.moznion.docuss.formatter.YAMLFormatterGenerator;
import net.moznion.docuss.presenter.StandardOutPresenter;

import me.geso.servlettester.jetty.JettyServletTester;

public class DocussTest {
    @Test
    public void shouldGetAndDescribeSuccessfully() throws Exception {
        final Docuss docuss = new Docuss(new YAMLFormatterGenerator(), new StandardOutPresenter());

        JettyServletTester.runServlet((req, resp) -> {
            resp.getWriter().print("{\"msg\": \"Hey\",\n\"value\": 100}");
        }, uri -> {
            try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                try (final Capturer capturer = new Capturer(out)) {
                    docuss.shouldGet(uri, resp -> {
                        assertEquals(200, resp.getStatusLine().getStatusCode());
                    });
                }
                assertTrue(out.toString().contains("statusCode: 200"));
            }
        });
    }
}
