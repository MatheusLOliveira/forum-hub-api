package com.forum_hub.api.domain.answer;

public record AnswerRequestDTO(
        String message,
        Long topicId
) {
}
