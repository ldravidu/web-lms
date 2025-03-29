package com.ldravidu.web_lms_backend.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ldravidu.web_lms_backend.entity.CourseContent;
import com.ldravidu.web_lms_backend.service.FileUploadService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/content")
public class CourseContentController {
    private final FileUploadService fileUploadService;

    public CourseContentController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<CourseContent> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("courseId") String courseId) {
        try {
            CourseContent content = fileUploadService.uploadFile(file, courseId);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @GetMapping("/")
    public String getContent() {
        return "Content";
    }

}
