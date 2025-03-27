package com.ldravidu.web_lms_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldravidu.web_lms_backend.entity.CourseContent;
import com.ldravidu.web_lms_backend.service.CourseContentService;

@RestController
@RequestMapping("/api/content")
public class CourseContentController {
    private final CourseContentService service;

    public CourseContentController(CourseContentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CourseContent> uploadContent(@RequestBody CourseContent content) {
        CourseContent saved = service.saveContent(content);
        return ResponseEntity.ok(saved);
    }
}
