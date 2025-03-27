package com.ldravidu.web_lms_backend.controller;

import org.springframework.mock.web.MockMultipartFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseContentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUploadFile() throws Exception {
        // Create a test PDF file
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "PDF content".getBytes());

        mockMvc.perform(multipart("/api/content/upload")
                .file(file)
                .param("courseId", "test_course"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").exists())
                .andExpect(jsonPath("$.fileType", is("application/pdf")))
                .andExpect(jsonPath("$.courseId", is("test_course")));
    }

    @Test
    void testInvalidFileType() throws Exception {
        // Create a test EXE file
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.exe",
                "application/octet-stream",
                "EXE content".getBytes());

        mockMvc.perform(multipart("/api/content/upload")
                .file(file)
                .param("courseId", "test_course"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLargeFile() throws Exception {
        // Create a large file
        byte[] largeContent = new byte[11 * 1024 * 1024]; // 11MB
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "large.pdf",
                "application/pdf",
                largeContent);

        mockMvc.perform(multipart("/api/content/upload")
                .file(file)
                .param("courseId", "test_course"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMissingFile() throws Exception {
        mockMvc.perform(multipart("/api/content/upload")
                .param("courseId", "test_course"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMissingCourseId() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "PDF content".getBytes());

        mockMvc.perform(multipart("/api/content/upload")
                .file(file))
                .andExpect(status().isBadRequest());
    }
}
