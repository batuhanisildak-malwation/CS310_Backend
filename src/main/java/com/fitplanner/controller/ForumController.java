package com.fitplanner.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitplanner.dto.get.GetTopicDetail;
import com.fitplanner.dto.get.GetTopicDetailComment;
import com.fitplanner.dto.post.AddComment;
import com.fitplanner.dto.post.CreateTopic;
import com.fitplanner.model.ForumComment;
import com.fitplanner.model.ForumTopic;
import com.fitplanner.model.User;
import com.fitplanner.payload.Data;
import com.fitplanner.payload.Message;
import com.fitplanner.repository.ForumCommentRepository;
import com.fitplanner.repository.ForumTopicRepository;
import com.fitplanner.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/forum")
public class ForumController {
    @Autowired
    ForumTopicRepository forumTopicRepository;
    @Autowired
    ForumCommentRepository forumCommentRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create-topic")
    public Message createTopic(@RequestBody CreateTopic dto) {
        Message message = new Message();
        ForumTopic topicFound = forumTopicRepository.findByTitle(dto.title);
        if (topicFound != null) {
            message.setReturnMessage("Topic already exists");
            message.setReturnCode(400);
            return message;

        }

        ForumTopic newlyTopic = new ForumTopic(dto.title);
        forumTopicRepository.save(newlyTopic);
        message.setReturnMessage("Topic created");
        message.setReturnCode(200);

        return message;
    }

    @GetMapping("/get-topics")
    public Data<List<ForumTopic>> getForumTopics() {
        List<ForumTopic> topics = forumTopicRepository.findAll();

        List<ForumTopic> topicReponseData = topics.stream()
                .map(topic -> new ForumTopic(topic.getId(), topic.getTitle()))
                .collect(Collectors.toList());

        Data<List<ForumTopic>> response = new Data<List<ForumTopic>>("Topic list is retrieved succesfully",
                200, topicReponseData);
        return response;
    }

    @GetMapping("/get-topic-detail/{topicId}")
    public Data<GetTopicDetail> getForumTopicDetail(@PathVariable("topicId") String topicId) {
        ForumTopic topicFound = forumTopicRepository.findById(topicId).orElse(null);
        if (topicFound == null) {
            Data<GetTopicDetail> response = new Data<GetTopicDetail>("Topic could not be found.", 404, null);
            return response;
        }
        List<ForumComment> comments = forumCommentRepository.findByTopic(topicFound);
        List<GetTopicDetailComment> commentsParsed = comments.stream().map(comment -> {
            User user = comment.getUser();
            GetTopicDetailComment commentModel = new GetTopicDetailComment(user.getUsername(), comment.getText());
            return commentModel;
        }).collect(Collectors.toList());

        GetTopicDetail topicData = new GetTopicDetail(topicFound.getTitle(), commentsParsed);
        Data<GetTopicDetail> response = new Data<GetTopicDetail>("Topic information succesfully obtained", 200,
                topicData);
        return response;
    }

    @PostMapping("/add-comment")
    public Message addComment(@RequestBody AddComment dto, @AuthenticationPrincipal User user) {
        Message message = new Message();

        ForumTopic forumTopic = forumTopicRepository.findById(dto.topicId).orElse(null);
        if (forumTopic == null) {
            message.setReturnMessage("Topic could not be found.");
            message.setReturnCode(404);
            return message;
        }

        ForumComment newComment = new ForumComment(user, forumTopic, dto.text);
        forumCommentRepository.save(newComment);
        message.setReturnMessage("Comment added succesfully.");
        message.setReturnCode(200);
        return message;
    }

}
