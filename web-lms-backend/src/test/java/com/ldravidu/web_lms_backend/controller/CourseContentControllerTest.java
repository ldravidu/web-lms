package com.ldravidu.web_lms_backend.controller;

import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldravidu.web_lms_backend.entity.CourseContent;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseContentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUploadContent() throws Exception {
        CourseContent content = new CourseContent();

        content.setFileName("test.pdf");
        content.setFileType("application/pdf");
        content.setFileSize(1024L);
        content.setUploadDate(LocalDateTime.now());
        content.setFileUrl("/uploads/test.pdf");

        mockMvc.perform(post("/api/content")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content))).andExpect(status().isOk());
    }
}
