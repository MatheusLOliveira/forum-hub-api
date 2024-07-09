package com.forum_hub.api.controller;

import com.forum_hub.api.domain.user.User;
import com.forum_hub.api.domain.user.UserRequestDTO;
import com.forum_hub.api.domain.user.UserResponseDTO;
import com.forum_hub.api.domain.user.UserUpdateDTO;
import com.forum_hub.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO, UriComponentsBuilder uriBuilder) {
        User user = userService.createUser(userRequestDTO);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var page = userService.getAllUsers(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        UserResponseDTO userResponseDTO = userService.getUserById(userId);
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody @Valid UserUpdateDTO updateDTO, @PathVariable Long userId) {
        UserResponseDTO updatedUser = userService.updateUser(updateDTO, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}