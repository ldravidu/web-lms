package com.ldravidu.web_lms_backend.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ldravidu.web_lms_backend.entity.CourseContent;

public interface FileUploadService {
    CourseContent uploadFile(MultipartFile file, String courseId) throws IOException;
}
