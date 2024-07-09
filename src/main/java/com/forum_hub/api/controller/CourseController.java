package com.forum_hub.api.controller;

import com.forum_hub.api.domain.course.Course;
import com.forum_hub.api.domain.course.CourseRequestDTO;
import com.forum_hub.api.domain.course.CourseResponseDTO;
import com.forum_hub.api.domain.course.CourseUpdateDTO;
import com.forum_hub.api.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Transactional
    public ResponseEntity<Course> createCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO, UriComponentsBuilder uriBuilder) {
        Course course = courseService.createCourse(courseRequestDTO);
        var uri = uriBuilder.path("/courses/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(uri).body(course);
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponseDTO>> getAllCourses(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var page = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long courseId) {
        CourseResponseDTO courseResponseDTO = courseService.getCourseById(courseId);
        return ResponseEntity.ok().body(courseResponseDTO);
    }

    @PutMapping("/{courseId}")
    @Transactional
    public ResponseEntity<CourseResponseDTO> updateCourse(@RequestBody @Valid CourseUpdateDTO updateDTO, @PathVariable Long courseId) {
        CourseResponseDTO updatedCourse = courseService.updateCourse(updateDTO, courseId);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{courseId}")
    @Transactional
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}