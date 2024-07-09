package com.forum_hub.api.domain.answer;

import java.time.LocalDateTime;

public record AnswerResponseDTO(
        Long id,
        String message,
        Long topicId,
        LocalDateTime createdAt,
        Long userId,
        Boolean solution
) {
    public AnswerResponseDTO(Answer answer) {
        this(
                answer.getId(),
                answer.getMessage(),
                answer.getTopic().getId(),
                answer.getCreatedAt(),
                answer.getUser().getId(),
                answer.getSolution()
        );
    }
}
