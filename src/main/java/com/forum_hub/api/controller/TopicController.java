package com.forum_hub.api.controller;

import com.forum_hub.api.domain.topic.Topic;
import com.forum_hub.api.domain.topic.TopicRequestDTO;
import com.forum_hub.api.domain.topic.TopicUpdateDTO;
import com.forum_hub.api.domain.topic.TopicResponseDTO;
import com.forum_hub.api.service.TopicService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    @Transactional
    public ResponseEntity<Topic> createTopic(Authentication authentication, @RequestBody @Valid TopicRequestDTO topicRequestDTO, UriComponentsBuilder uriBuilder) {
        Topic topic = topicService.createTopic(topicRequestDTO, authentication);
        var uri = uriBuilder.path("topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(topic);
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponseDTO>> getAllTopics(@PageableDefault(size = 10, sort = {"createdAt"}) Pageable pageable) {
        var page = topicService.getAllTopics(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicResponseDTO> getTopicById(@PathVariable Long topicId) {
        TopicResponseDTO topicResponseDTO = topicService.getTopicById(topicId);
        return ResponseEntity.ok().body(topicResponseDTO);
    }

    @PutMapping("/{topicId}")
    @Transactional
    public ResponseEntity<TopicResponseDTO> updateTopic(@RequestBody @Valid TopicUpdateDTO updateDTO, @PathVariable Long topicId, Authentication authentication) {
        TopicResponseDTO updatedTopic = topicService.updateTopic(updateDTO, topicId, authentication);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{topicId}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicId, Authentication authentication) {
        topicService.deleteTopic(topicId, authentication);
        return ResponseEntity.noContent().build();
    }
}