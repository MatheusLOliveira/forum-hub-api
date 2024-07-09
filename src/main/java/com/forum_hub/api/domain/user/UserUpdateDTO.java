package com.forum_hub.api.domain.user;

public record UserUpdateDTO(
        String name,
        String email,
        String password

) {
}
