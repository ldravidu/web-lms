package com.ldravidu.web_lms_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.ldravidu.web_lms_backend.entity.CourseContent;

@SpringBootTest
public class FileUploadServiceTest {
    @Autowired
    private FileUploadService fileUploadService;

    @Test
    void testUploadFile() throws IOException {
        // Create a test PDF file
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "PDF content".getBytes());

        // Upload the file
        CourseContent content = fileUploadService.uploadFile(file, "test_course");
        assertNotNull(content);
        assertTrue(content.getFileName().endsWith(".pdf"));
        assertEquals("application/pdf", content.getFileType());
        assertNotNull(content.getUploadDate());
        assertNotNull(content.getFileUrl());
        assertEquals("test_course", content.getCourseId());
    }

    @Test
    void testUploadSameFileNameTwice() throws IOException {
        // First upload
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "PDF content".getBytes());

        CourseContent firstUpload = fileUploadService.uploadFile(file, "test_course");
        assertNotNull(firstUpload);
        String firstFileName = firstUpload.getFileName();

        // Second upload with same file
        CourseContent secondUpload = fileUploadService.uploadFile(file, "test_course");
        assertNotNull(secondUpload);
        String secondFileName = secondUpload.getFileName();

        // Verify different filenames but same extension
        assertNotEquals(firstFileName, secondFileName);
        assertTrue(firstFileName.endsWith(".pdf"));
        assertTrue(secondFileName.endsWith(".pdf"));
    }

    @Test
    void testInvalidFileType() {
        // Create a test file with an invalid file type
        MockMultipartFile file = new MockMultipartFile("file", "test.exe", "application/octet-stream",
                "EXE content".getBytes());

        assertThrows(IllegalArgumentException.class, () -> fileUploadService.uploadFile(file, "test_course"));
    }

    @Test
    void testInvalidFileSize() throws IOException {
        // Create a test file with an invalid file size
        byte[] largeContent = new byte[11 * 1024 * 1024];
        MockMultipartFile file = new MockMultipartFile("file", "large.pdf", "application/pdf", largeContent);

        assertThrows(IllegalArgumentException.class, () -> fileUploadService.uploadFile(file, "test_course"));
    }
}
