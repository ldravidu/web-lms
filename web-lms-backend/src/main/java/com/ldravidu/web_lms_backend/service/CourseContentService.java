package com.ldravidu.web_lms_backend.service;

import org.springframework.stereotype.Service;

import com.ldravidu.web_lms_backend.entity.CourseContent;
import com.ldravidu.web_lms_backend.repository.CourseContentRepository;

@Service
public class CourseContentService {
    private final CourseContentRepository repository;

    public CourseContentService(CourseContentRepository repository) {
        this.repository = repository;
    }

    public CourseContent saveContent(CourseContent content) {
        return repository.save(content);
    }
}
