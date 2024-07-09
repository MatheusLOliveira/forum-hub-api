package com.forum_hub.api.service;

import com.forum_hub.api.domain.course.Course;
import com.forum_hub.api.domain.course.CourseRequestDTO;
import com.forum_hub.api.domain.course.CourseResponseDTO;
import com.forum_hub.api.domain.course.CourseUpdateDTO;
import com.forum_hub.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(CourseRequestDTO courseRequestDTO) {
        Course course = new Course();
        course.setName(courseRequestDTO.name());
        course.setCategory(courseRequestDTO.category());
        return courseRepository.save(course);
    }

    public CourseResponseDTO getCourseById(Long courseId) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course with id " + courseId + " not found"));
        return new CourseResponseDTO(course);
    }

    public Course getCourseByName(String name) {
        return courseRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(CourseResponseDTO::new);
    }

    public CourseResponseDTO updateCourse(CourseUpdateDTO updateDTO, Long courseId) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (updateDTO.name() != null) {
            course.setName(updateDTO.name());
        }
        if (updateDTO.category() != null) {
            course.setCategory(updateDTO.category());
        }

        return new CourseResponseDTO(courseRepository.save(course));
    }

    public void deleteCourse(Long courseId) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.deleteById(courseId);
    }
}