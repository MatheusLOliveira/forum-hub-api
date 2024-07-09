package com.forum_hub.api.domain.answer;

public record AnswerUpdateDTO(
        String message,
        Boolean solution
) {
}
