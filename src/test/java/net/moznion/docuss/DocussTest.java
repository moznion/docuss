package net.moznion.docuss;

import me.geso.servlettester.jetty.JettyServletTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocussTest {
    private final Docuss docuss = new Docuss();

    @Test
    public void shouldAlwaysPass() throws Exception {
        JettyServletTester.runServlet((req, resp) -> {
            resp.getWriter().print("{\"msg\": \"Hey\",\n\"value\": 100}");
        }, uri -> {
            docuss.shouldGet(uri, resp -> {
                assertEquals(200, resp.getStatusLine().getStatusCode());
            });
        });
    }
}
