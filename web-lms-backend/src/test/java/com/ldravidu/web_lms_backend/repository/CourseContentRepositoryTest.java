package com.ldravidu.web_lms_backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ldravidu.web_lms_backend.entity.CourseContent;

@DataJpaTest
@SpringJUnitConfig
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseContentRepositoryTest {
    @Autowired
    private CourseContentRepository repository;

    @Test
    void testSaveAndFindContent() {
        CourseContent content = new CourseContent();
        content.setFileName("test.pdf");
        content.setFileType("application/pdf");
        content.setFileSize(1024L);
        content.setUploadDate(LocalDateTime.now());
        content.setFileUrl("/uploads/test.pdf");

        CourseContent saved = repository.save(content);
        assertNotNull(saved);
        assertNotNull(saved.getId());

        CourseContent found = repository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(content.getFileName(), found.getFileName());
    }
}
