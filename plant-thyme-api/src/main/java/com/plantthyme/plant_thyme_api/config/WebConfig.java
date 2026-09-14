package com.plantthyme.plant_thyme_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// This class configures how Spring serves files over HTTP.
// The backend SAVES uploaded images to a folder, but that alone doesn't make them viewable.
// This is what lets the browser actually GET an image by URL (the "serve it back" step).
// @Configuration = a class that sets up app config; WebMvcConfigurer = interface for customizing web behavior.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // pulls the folder path from application.properties (file.upload-dir), same value the upload service uses.
    @Value("${file.upload-dir}")
    private String uploadDir;

    // maps a URL path to a folder on disk. Two parts: the URL people request, and where to find the file.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                // THE URL — requests to /uploads/progress-pictures/<anything> get handled here. ** = any filename.
                .addResourceHandler("/uploads/progress-pictures/**")
                // THE FOLDER — serve those requests from this real folder on disk.
                // "file:" prefix = it's a filesystem folder (not a file bundled inside the app). Trailing "/" is required.
                .addResourceLocations("file:" + uploadDir + "/");
    }
}