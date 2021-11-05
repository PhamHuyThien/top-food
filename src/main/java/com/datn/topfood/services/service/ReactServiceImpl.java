package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Comment;
import com.datn.topfood.data.model.Post;
import com.datn.topfood.data.repository.jpa.CommentRepository;
import com.datn.topfood.data.repository.jpa.PostRepository;
import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.services.interf.ReactService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ReactServiceImpl implements ReactService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @Override
    @Transactional
    public Void commentPost(CommentPostRequest commentPostRequest, Account itMe) {
        Optional<Post> optionalPost = postRepository.findById(commentPostRequest.getIdPost());
        if (!optionalPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.POST_NOT_EXISTS);
        }
        Timestamp currentTime = DateUtils.currentTimestamp();
        Comment comment = new Comment();
        comment.setContent(comment.getContent());
        comment.setCreateAt(currentTime);
        comment = commentRepository.save(comment);
//        commentPostRequest.getFiles().forEach(file-> );

        return null;
    }
}
