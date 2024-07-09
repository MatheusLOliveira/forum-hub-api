package com.forum_hub.api.service;

import com.forum_hub.api.domain.user.User;
import com.forum_hub.api.domain.user.UserRequestDTO;
import com.forum_hub.api.domain.user.UserResponseDTO;
import com.forum_hub.api.domain.user.UserUpdateDTO;
import com.forum_hub.api.repository.TopicRepository;
import com.forum_hub.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Long DELETED_ACCOUNT_ID = 0L;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        return userRepository.save(user);
    }

    public UserResponseDTO getUserById(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));
        return new UserResponseDTO(user);
    }

    public UserDetails getUserByLogin(String login) {
        return userRepository.findByEmail(login)
                .orElseThrow(() -> new RuntimeException("User with login " + login + " not found"));
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponseDTO::new);
    }

    public UserResponseDTO updateUser(UserUpdateDTO updateDTO, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updateDTO.name() != null) {
            user.setName(updateDTO.name());
        }
        if (updateDTO.email() != null) {
            user.setEmail(updateDTO.email());
        }
        if (updateDTO.password() != null) {
            user.setPassword(updateDTO.password());
        }

        return new UserResponseDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        topicRepository.updateUserToDeletedAccount(userId, DELETED_ACCOUNT_ID);

        userRepository.deleteById(userId);
    }
}