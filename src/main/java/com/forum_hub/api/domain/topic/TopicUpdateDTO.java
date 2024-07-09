package com.forum_hub.api.domain.topic;

import com.forum_hub.api.domain.topic.status.Status;

public record TopicUpdateDTO(
        String title,
        String message,
        Status status
) {
}
