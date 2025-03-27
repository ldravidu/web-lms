package com.ldravidu.web_lms_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ldravidu.web_lms_backend.entity.CourseContent;

@SpringBootTest
public class CourseContentServiceTest {
    @Autowired
    private CourseContentService service;

    @Test
    void testSaveContent() {
        CourseContent content = new CourseContent();
        content.setFileName("test.pdf");
        content.setFileType("application/pdf");
        content.setFileSize(1024L);
        content.setUploadDate(LocalDateTime.now());
        content.setFileUrl("/uploads/test.pdf");

        CourseContent saved = service.saveContent(content);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(content.getFileName(), saved.getFileName());
    }
}
