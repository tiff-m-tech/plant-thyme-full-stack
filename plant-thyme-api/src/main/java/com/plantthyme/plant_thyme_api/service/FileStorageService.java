package com.plantthyme.plant_thyme_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

// video referenced: https://www.youtube.com/watch?v=mVndHU4MbQw, only need first half with uploading, second half is downloading
// @Service marks this as a business-logic layer, sitting between controller and repository.
// Same idea as my frontend service functions — pull the "real work" out so the controller stays focused
// @Service lets Spring manage it so I can inject it into the controller (like my repositories).

@Service
public class FileStorageService {
    // @Value pulls the folder path from application.properties (file.upload-dir), so the location lives in config, not hardcoded here.
    @Value("${file.upload-dir}")
    private String uploadDir;

    // Takes the uploaded file, saves it to the folder, returns the generated filename.
    // The controller stores that filename in the DB (imagePath).
    public String storeFile(MultipartFile file) {
        try {
            // Pull the file extension (.webp, .jpg) off the original filename so we keep the file type.
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            String original = file.getOriginalFilename();
            String extension = (original != null && original.contains("."))
                    ? original.substring(original.lastIndexOf(".")) : "";

            // Generate a UNIQUE filename with UUID so two uploads named "photo.jpg" don't overwrite each other.
            String filename = UUID.randomUUID() + extension;

            // Actually write the file's bytes to disk at folder/filename.
            // file.getInputStream() = the uploaded bytes; Files.copy does the saving.
            Path destination = uploadPath.resolve(filename);

            System.out.println("SAVING FILE TO: " + destination.toAbsolutePath());

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            // File I/O can fail (disk, permissions), so wrap it in a runtime exception.
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }
}