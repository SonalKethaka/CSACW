package config;
//public class config.Main {
//    public static void main(String[] args) {
//
//        System.out.printf("Hello and welcome!");
//    }
//}


import config.MainApplication;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://localhost:8080/api/";

    public static HttpServer startServer() {
        // Scan all packages automatically
        final ResourceConfig rc = new ResourceConfig().packages("resource", "exception");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("🚀 Server running at " + BASE_URI);
        System.out.println("📭 Press ENTER to stop the server...");
        System.in.read();
        server.shutdownNow();
    }
}