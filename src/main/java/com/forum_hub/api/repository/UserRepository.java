package com.forum_hub.api.repository;

import com.forum_hub.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByEmail(String email);

    Optional<UserDetails> findByEmail(String email);
}
