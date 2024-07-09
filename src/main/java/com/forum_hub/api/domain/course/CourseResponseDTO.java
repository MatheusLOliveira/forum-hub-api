package com.forum_hub.api.domain.course;

public record CourseResponseDTO(
        Long id,
        String name,
        Category category
) {

    public CourseResponseDTO(Course course) {
        this(
                course.getId(),
                course.getName(),
                course.getCategory()
        );
    }

}
