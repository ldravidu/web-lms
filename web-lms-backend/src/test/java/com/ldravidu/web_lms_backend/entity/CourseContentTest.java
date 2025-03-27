package com.ldravidu.web_lms_backend.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class CourseContentTest {
    @Test
    void testCourseContentCreation() {
        CourseContent content = new CourseContent();
        content.setFileName("test.pdf");
        content.setFileType("application/pdf");
        content.setFileSize(1024L);
        content.setUploadDate(LocalDateTime.now());
        content.setFileUrl("/uploads/test.pdf");

        assertNotNull(content);
        assertEquals("test.pdf", content.getFileName());
        assertEquals("application/pdf", content.getFileType());
        assertEquals(1024L, content.getFileSize());
        assertNotNull(content.getUploadDate());
        assertEquals("/uploads/test.pdf", content.getFileUrl());
    }
}
