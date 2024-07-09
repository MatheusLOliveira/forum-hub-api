package com.forum_hub.api.service;

import com.forum_hub.api.domain.course.Course;
import com.forum_hub.api.domain.topic.Topic;
import com.forum_hub.api.domain.topic.TopicRequestDTO;
import com.forum_hub.api.domain.topic.TopicUpdateDTO;
import com.forum_hub.api.domain.topic.status.Status;
import com.forum_hub.api.domain.topic.TopicResponseDTO;
import com.forum_hub.api.domain.user.User;
import com.forum_hub.api.domain.user.UserResponseDTO;
import com.forum_hub.api.repository.TopicRepository;
import com.forum_hub.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public Topic createTopic(TopicRequestDTO topicRequestDTO, Authentication authentication) {
        Course course = courseService.getCourseByName(topicRequestDTO.courseName());

        var userId = Long.parseLong(authentication.getCredentials().toString());
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = new Topic();
        topic.setTitle(topicRequestDTO.title());
        topic.setMessage(topicRequestDTO.message());
        topic.setCreatedAt(LocalDateTime.now());
        topic.setStatus(Status.NOTSOLVED);
        topic.setUser(user);
        topic.setCourse(course);

        return topicRepository.save(topic);
    }

    public TopicResponseDTO getTopicById(Long topicId) {
        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic with id " + topicId + " not found"));

        TopicResponseDTO topicResponseDTO = new TopicResponseDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getStatus(),
                new UserResponseDTO(
                        topic.getUser().getId(),
                        topic.getUser().getName(),
                        topic.getUser().getEmail()
                ),
                topic.getCourse()
        );

        return topicResponseDTO;
    }

    public Page<TopicResponseDTO> getAllTopics(Pageable pageable) {
        Page<Topic> topics = topicRepository.findAllByOrderByCreatedAtDesc(pageable);

        return topics.map(TopicResponseDTO::new);
    }

    public TopicResponseDTO updateTopic(TopicUpdateDTO updateDTO, Long topicId, Authentication authentication) {
        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        var userId = authentication.getCredentials().toString();

        if (!topic.getUser().getId().toString().equals(userId)) {
            throw new RuntimeException("You do not have permission to perform this action");
        }

        if (updateDTO.title() != null) {
            topic.setTitle(updateDTO.title());
        }
        if (updateDTO.message() != null) {
            topic.setMessage(updateDTO.message());
        }
        if (updateDTO.status() != null) {
            topic.setStatus(updateDTO.status());
        }

        return new TopicResponseDTO(topicRepository.save(topic));
    }

    public void deleteTopic(Long topicId, Authentication authentication) {
        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        var userId = authentication.getCredentials().toString();

        if (!topic.getUser().getId().toString().equals(userId)) {
            throw new RuntimeException("You do not have permission to perform this action");
        }

        topicRepository.deleteById(topicId);
    }
}