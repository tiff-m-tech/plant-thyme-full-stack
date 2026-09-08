package com.plantthyme.plant_thyme_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;

// CORS config: lets the React app (a different origin) call this backend.
// Browsers block cross-origin requests by default; this says which origins are allowed.
// An origin is the combination of protocol + domain + port of a web address. So:
// http://localhost:5173 is one origin (your React app)
// http://localhost:8080 is a different origin (your Spring backend)
// "Cross-origin" just means a request going from one origin to a different one.
// React app (:5173) makes a fetch call to the backend (:8080), that request crosses from one origin to another, blocked by default for security.
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // Filter that checks every incoming request against the CORS rules below.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow cookies/auth headers to be sent (needed later if I add login).
        config.setAllowCredentials(true);

        // Allow requests from the React dev server (Vite's default port).
        config.setAllowedOriginPatterns(List.of("http://localhost:5173"));

        // Allow all request types (GET, POST, PUT, DELETE) and all headers.
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        // Apply these rules to every path in the app.
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}