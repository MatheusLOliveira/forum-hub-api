ALTER TABLE topic
    ADD COLUMN user_id BIGINT NOT NULL,
    ADD COLUMN course_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_topic_user FOREIGN KEY (user_id) REFERENCES user(id),
    ADD CONSTRAINT fk_topic_course FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE answer
    ADD COLUMN user_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_answer_user FOREIGN KEY (user_id) REFERENCES user(id);


