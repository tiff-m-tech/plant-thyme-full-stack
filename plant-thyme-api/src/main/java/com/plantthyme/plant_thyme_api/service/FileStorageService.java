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

// Takes the uploaded file, saves it to the folder, returns the generated filename, controller stores that filename in the DB (imagePath).

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        try {
            // Make sure the upload folder exists (creates it if it doesn't).
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            // Pull the file extension (.webp, .jpg) off the original filename so we keep the file type.
            String original = file.getOriginalFilename();
            String extension = (original != null && original.contains("."))
                    ? original.substring(original.lastIndexOf(".")) : "";

            // Generate a UNIQUE filename with UUID so two uploads named "photo.jpg" don't overwrite each other.
            String filename = UUID.randomUUID() + extension;

            // Build the full path (folder/filename), then write the uploaded bytes to disk there.
            // file.getInputStream() = the uploaded bytes; Files.copy does the actual saving.
            Path destination = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }
}