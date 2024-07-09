package com.forum_hub.api.domain.user;

public record AuthenticationDTO(
        String login,
        String password
) {
}
