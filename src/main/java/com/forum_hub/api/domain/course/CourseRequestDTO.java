package com.forum_hub.api.domain.course;

import jakarta.validation.constraints.NotNull;

public record CourseRequestDTO(
        @NotNull
        String name,
        @NotNull
        Category category
) {
}
