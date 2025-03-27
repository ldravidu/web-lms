package com.ldravidu.web_lms_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ldravidu.web_lms_backend.entity.CourseContent;

@Repository
public interface CourseContentRepository extends JpaRepository<CourseContent, Long> {

}
