package com.fitplanner.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitplanner.model.ForumTopic;

public interface ForumTopicRepository extends MongoRepository<ForumTopic, String> {
    public ForumTopic findByTitle(String title);
}