package net.moznion.docuss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Test;

import net.moznion.capture.output.stream.Capturer;
import net.moznion.docuss.formatter.YAMLFormatterGenerator;
import net.moznion.docuss.httpclient.SimpleApacheHttpclient;
import net.moznion.docuss.presenter.FileOutPresenter;
import net.moznion.docuss.presenter.StandardOutPresenter;

import me.geso.servlettester.jetty.JettyServletTester;

public class DocussTest {
    @Test
    public void shouldGetAndDescribeSuccessfully() throws Exception {
        final Docuss<HttpEntity, HttpResponse> docuss = new Docuss<>(new YAMLFormatterGenerator(),
                                                                     new StandardOutPresenter(),
                                                                     new SimpleApacheHttpclient());

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

    @Test
    public void shouldGetAndDescribeToFileSuccessfully() throws Exception {
        final Path path = Paths.get(System.getProperty("user.dir"), "doc", "out.yml");
        Files.deleteIfExists(path);

        final FileOutPresenter fileOutPresenter = new FileOutPresenter(path);
        final Docuss<HttpEntity, HttpResponse> docuss = new Docuss<>(new YAMLFormatterGenerator(),
                                                                     fileOutPresenter,
                                                                     new SimpleApacheHttpclient());

        JettyServletTester.runServlet((req, resp) -> {
            resp.getWriter().print("{\"msg\": \"Hey\",\n\"value\": 100}");
        }, uri -> {
            docuss.shouldGet(uri, resp -> {
                assertEquals(200, resp.getStatusLine().getStatusCode());
            });
        });

        final String out = new String(Files.readAllBytes(path));
        assertTrue(out.contains("statusCode: 200"));
    }
}
