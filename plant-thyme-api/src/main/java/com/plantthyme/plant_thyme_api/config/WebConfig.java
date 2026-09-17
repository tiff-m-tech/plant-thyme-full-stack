package com.plantthyme.plant_thyme_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// This class configures how Spring serves files over HTTP.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // pulls the folder path from application.properties, same value the upload service uses.
    @Value("${file.upload-dir}")
    private String uploadDir;

    // maps a URL path to a folder on disk. Two parts: the URL people request, and where to find the file.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry

                .addResourceHandler("/uploads/progress-pictures/**")

                // The real disk folder to serve them from. "file:" = filesystem path, trailing "/" required.
                .addResourceLocations("file:" + uploadDir + "/");
    }
}