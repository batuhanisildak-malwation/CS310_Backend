package com.fitplanner.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitplanner.model.ForumComment;
import com.fitplanner.model.ForumTopic;
import com.fitplanner.model.User;

public interface ForumCommentRepository extends MongoRepository<ForumComment, String> {
    public List<ForumComment> findByUser(User user);

    public List<ForumComment> findByTopic(ForumTopic topic);
}
