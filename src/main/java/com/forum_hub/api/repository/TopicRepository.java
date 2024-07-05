package com.forum_hub.api.repository;

import com.forum_hub.api.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic findByTitle(String title);

}
