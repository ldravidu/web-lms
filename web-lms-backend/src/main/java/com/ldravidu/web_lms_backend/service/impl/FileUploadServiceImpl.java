package com.ldravidu.web_lms_backend.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ldravidu.web_lms_backend.entity.CourseContent;
import com.ldravidu.web_lms_backend.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final Path fileStorageLocation;

    public FileUploadServiceImpl(@Value("${file.storage.location:${user.home}/uploads}") String storageLocation) {
        this.fileStorageLocation = Paths.get(storageLocation).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create upload directory", ex);
        }
    }

    @Override
    public CourseContent uploadFile(MultipartFile file, String courseId) throws IOException {
        validateFile(file);

        String fileName = generateFileName(file.getOriginalFilename());
        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        // Copy file to the target location (Replacing existing file with the same name)
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        CourseContent content = new CourseContent();
        content.setFileName(fileName);
        content.setFileType(file.getContentType());
        content.setFileSize(file.getSize());
        content.setUploadDate(LocalDateTime.now());
        content.setFileUrl(targetLocation.toString());
        content.setCourseId(courseId);
        return content;
    }

    private void validateFile(MultipartFile file) {
        // Check file size (10 MB limit)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("File size limit exceeded (10 MB)");
        }

        // Check file type
        String contentType = file.getContentType();
        if (contentType == null || !isValidFileType(contentType)) {
            throw new IllegalArgumentException("Invalid file type");
        }
    }

    private boolean isValidFileType(String contentType) {
        String[] validTypes = {
                "application/pdf",
                "video/mp4",
                "image/jpeg",
                "image/png"
        };

        for (String type : validTypes) {
            if (type.equals(contentType)) {
                return true;
            }
        }

        return false;
    }

    private String generateFileName(String originalFilename) {
        String fileName = originalFilename;
        String fileExtension = getFileExtension(originalFilename);

        if (Files.exists(this.fileStorageLocation.resolve(fileName))) {
            fileName = UUID.randomUUID().toString() + fileExtension;
        }

        return fileName;
    }

    private String getFileExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf('.');

        if (extensionIndex > 0) {
            return fileName.substring(extensionIndex);
        }

        return "";
    }
}
