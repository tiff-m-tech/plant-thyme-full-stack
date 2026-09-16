package com.plantthyme.plant_thyme_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;

// Cross-origin/CORS config: lets the React app (a different origin :5173) call this backend (:8080).
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // Filter that checks every incoming request against the CORS rules below.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow cookies/auth headers to be sent (needed later for adding login).
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