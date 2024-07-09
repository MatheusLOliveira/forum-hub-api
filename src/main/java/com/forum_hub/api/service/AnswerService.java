package com.forum_hub.api.service;

import com.forum_hub.api.domain.answer.Answer;
import com.forum_hub.api.domain.answer.AnswerRequestDTO;
import com.forum_hub.api.domain.answer.AnswerResponseDTO;
import com.forum_hub.api.domain.answer.AnswerUpdateDTO;
import com.forum_hub.api.repository.AnswerRepository;
import com.forum_hub.api.repository.TopicRepository;
import com.forum_hub.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    public Answer createAnswer(AnswerRequestDTO answerRequestDTO, Authentication authentication) {
        var topic = topicRepository.findById(answerRequestDTO.topicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        var userId = Long.parseLong(authentication.getCredentials().toString());
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Answer answer = new Answer();
        answer.setMessage(answerRequestDTO.message());
        answer.setTopic(topic);
        answer.setCreatedAt(LocalDateTime.now());
        answer.setUser(user);
        answer.setSolution(false);

        return answerRepository.save(answer);
    }

    public AnswerResponseDTO getAnswerById(Long answerId) {
        var answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer with id " + answerId + " not found"));
        return new AnswerResponseDTO(answer);
    }

    public Page<AnswerResponseDTO> getAllAnswers(Pageable pageable) {
        return answerRepository.findAll(pageable).map(AnswerResponseDTO::new);
    }

    public AnswerResponseDTO updateAnswer(AnswerUpdateDTO updateDTO, Long answerId, Authentication authentication) {
        var answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
        var userId = authentication.getCredentials().toString();

        if (!answer.getUser().getId().toString().equals(userId)) {
            throw new RuntimeException("You do not have permission to perform this action");
        }

        if (updateDTO.message() != null) {
            answer.setMessage(updateDTO.message());
        }
        if (updateDTO.solution() != null) {
            answer.setSolution(updateDTO.solution());
        }

        return new AnswerResponseDTO(answerRepository.save(answer));
    }

    public void deleteAnswer(Long answerId, Authentication authentication) {
        var answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
        var userId = authentication.getCredentials().toString();

        if (!answer.getUser().getId().toString().equals(userId)) {
            throw new RuntimeException("You do not have permission to perform this action");
        }

        answerRepository.deleteById(answerId);
    }
}