package com.forum_hub.api.domain.topic;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.forum_hub.api.domain.answer.Answer;
import com.forum_hub.api.domain.course.Course;
import com.forum_hub.api.domain.topic.status.Status;
import com.forum_hub.api.domain.topic.status.StatusConverter;
import com.forum_hub.api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;

    @Column(name = "creation_date")
    private LocalDateTime createdAt;

//    @Enumerated(EnumType.STRING)
    @Convert(converter = StatusConverter.class)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Answer> answers;
}
