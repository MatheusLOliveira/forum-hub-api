package com.forum_hub.api.controller;

import com.forum_hub.api.domain.answer.Answer;
import com.forum_hub.api.domain.answer.AnswerRequestDTO;
import com.forum_hub.api.domain.answer.AnswerResponseDTO;
import com.forum_hub.api.domain.answer.AnswerUpdateDTO;
import com.forum_hub.api.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    @Transactional
    public ResponseEntity<Answer> createAnswer(Authentication authentication, @RequestBody @Valid AnswerRequestDTO answerRequestDTO, UriComponentsBuilder uriBuilder) {
        Answer answer = answerService.createAnswer(answerRequestDTO, authentication);
        var uri = uriBuilder.path("/answers/{id}").buildAndExpand(answer.getId()).toUri();
        return ResponseEntity.created(uri).body(answer);
    }

    @GetMapping
    public ResponseEntity<Page<AnswerResponseDTO>> getAllAnswers(@PageableDefault(size = 10, sort = {"createdAt"}) Pageable pageable) {
        var page = answerService.getAllAnswers(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDTO> getAnswerById(@PathVariable Long answerId) {
        AnswerResponseDTO answerResponseDTO = answerService.getAnswerById(answerId);
        return ResponseEntity.ok().body(answerResponseDTO);
    }

    @PutMapping("/{answerId}")
    @Transactional
    public ResponseEntity<AnswerResponseDTO> updateAnswer(@RequestBody @Valid AnswerUpdateDTO updateDTO, @PathVariable Long answerId, Authentication authentication) {
        AnswerResponseDTO updatedAnswer = answerService.updateAnswer(updateDTO, answerId, authentication);
        return ResponseEntity.ok(updatedAnswer);
    }

    @DeleteMapping("/{answerId}")
    @Transactional
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId, Authentication authentication) {
        answerService.deleteAnswer(answerId, authentication);
        return ResponseEntity.noContent().build();
    }
}