package com.forum_hub.api.domain.topic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forum_hub.api.domain.course.Course;
import com.forum_hub.api.domain.topic.status.Status;
import com.forum_hub.api.domain.user.UserResponseDTO;

import java.time.LocalDateTime;

public record TopicResponseDTO(
        Long id,
        String title,
        String message,
        @JsonProperty("creation_date")
        LocalDateTime createdAt,
        Status status,
        UserResponseDTO userResponseDTO,
        Course course
) {

        public TopicResponseDTO(Topic topic) {
                this(
                        topic.getId(),
                        topic.getTitle(),
                        topic.getMessage(),
                        topic.getCreatedAt(),
                        topic.getStatus(),
                        new UserResponseDTO(
                                topic.getUser().getId(),
                                topic.getUser().getName(),
                                topic.getUser().getEmail()
                        ),
                        topic.getCourse());
        }

}
