package com.datn.topfood.services.service;

import com.datn.topfood.data.model.*;
import com.datn.topfood.data.repository.jpa.*;
import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.request.ReactPostRequest;
import com.datn.topfood.services.interf.ReactService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReactServiceImpl implements ReactService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    CommentPostRepository commentPostRepository;
    @Autowired
    ReactionPostRepository reactionPostRepository;
    @Autowired
    ReactionRepository reactionRepository;

    @Override
    @Transactional
    public Void commentPost(Long id, CommentPostRequest commentPostRequest, Account itMe) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.POST_NOT_EXISTS);
        }
        Timestamp currentTime = DateUtils.currentTimestamp();
        Comment comment = new Comment();
        comment.setContent(commentPostRequest.getContent());
        comment.setCreateAt(currentTime);
        Set<File> fileSet = commentPostRequest.getFiles().stream().map(strFile -> {
            File file = new File();
            file.setPath(strFile);
            file.setCreateAt(currentTime);
            return fileRepository.save(file);
        }).collect(Collectors.toSet());
        comment.setFiles(fileSet);
        comment.setAccount(itMe);
        comment = commentRepository.save(comment);
        CommentPost commentPost = new CommentPost();
        commentPost.setComment(comment);
        commentPost.setPost(optionalPost.get());
        commentPost.setCreateAt(currentTime);
        commentPostRepository.save(commentPost);
        return null;
    }

    @Override
    @Transactional
    public Void reactPost(Long id, ReactPostRequest reactPostRequest, Account itMe) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.POST_NOT_EXISTS);
        }
        Timestamp currentTime = DateUtils.currentTimestamp();
        Reaction reaction = new Reaction();
        reaction.setAccount(itMe);
        reaction.setCreateAt(currentTime);
        reaction.setType(reactPostRequest.getType());
        reaction = reactionRepository.save(reaction);
        ReactionPost reactionPost = new ReactionPost();
        reactionPost.setReaction(reaction);
        reactionPost.setPost(optionalPost.get());
        reactionPostRepository.save(reactionPost);
        return null;
    }
}
