package no.bekk.jetty;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WebServerMain {

	private static final Logger LOG = LoggerFactory.getLogger(WebServerMain.class);
	private static final int SERVER_PORT = 1234;

	public static void main(final String[] args) throws IOException {

        Flyway flyway = new Flyway();
        flyway.setDataSource(System.getenv("BOXFUSE_DATABASE_URL"), System.getenv("BOXFUSE_DATABASE_USER"), System.getenv("BOXFUSE_DATABASE_PASSWORD"));
        flyway.migrate();

		Server server = new Server(SERVER_PORT);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		ServletHolder h = new ServletHolder(new ServletContainer());
		h.setInitParameter("com.sun.jersey.config.property.packages", "no.bekk.jersey.resources");
		h.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		context.addServlet(h, "/*");
		try {
			server.start();
			LOG.info("Server started on port " + SERVER_PORT);
			server.join();
		} catch (Exception e) {
			LOG.error("Could not start server!", e);
		}
	}
}
