package com.forum_hub.api.domain.topic;

public record TopicRequestDTO(
        String title,
        String message,
        String courseName
) {
}
