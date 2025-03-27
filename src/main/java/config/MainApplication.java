package config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")  // base path for all endpoints
public class MainApplication extends Application {
    // no code needed here
}