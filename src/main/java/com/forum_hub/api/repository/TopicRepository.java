package com.forum_hub.api.repository;

import com.forum_hub.api.domain.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic findByTitle(String title);

    Page<Topic> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Modifying
    @Query("UPDATE Topic t SET t.user.id = :deletedAccountId WHERE t.user.id = :userId")
    void updateUserToDeletedAccount(@Param("userId") Long userId, @Param("deletedAccountId") Long deletedAccountId);

}
