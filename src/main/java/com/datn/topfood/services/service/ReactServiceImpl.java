package com.datn.topfood.services.service;

import com.datn.topfood.data.model.*;
import com.datn.topfood.data.repository.jpa.*;
import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ReactionRequest;
import com.datn.topfood.dto.response.CommentResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.services.interf.ReactService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    CommentFileRepository commentFileRepository;
    @Autowired
    CommentReactionRepository commentReactionRepository;
    @Autowired
    CommentReplyRepository commentReplyRepository;

    @Override
    @Transactional
    public Void commentPost(Long id, CommentPostRequest commentPostRequest, Account itMe) {
        Post post = checkPostExists(id);
        Comment comment = savedComment(commentPostRequest, itMe);
        CommentPost commentPost = new CommentPost();
        commentPost.setComment(comment);
        commentPost.setPost(post);
        commentPostRepository.save(commentPost);
        return null;
    }

    @Override
    @Transactional
    public Void reactPost(Long id, ReactionRequest reactPostRequest, Account itMe) {
        Post post = checkPostExists(id);
        Timestamp currentTime = DateUtils.currentTimestamp();
        Reaction reaction = new Reaction();
        reaction.setAccount(itMe);
        reaction.setCreateAt(currentTime);
        reaction.setType(reactPostRequest.getType());
        reaction = reactionRepository.save(reaction);
        ReactionPost reactionPost = new ReactionPost();
        reactionPost.setReaction(reaction);
        reactionPost.setPost(post);
        reactionPostRepository.save(reactionPost);
        return null;
    }

    @Override
    public PageResponse<ProfileResponse> listReactionPost(Long id, PageRequest pageRequest, Account itMe) {
        Pageable pageable = PageUtils.toPageable(pageRequest);
        Post post = checkPostExists(id);
        Page<Reaction> reactionPage = reactionRepository.findAllByReactionPostId(post.getId(), pageable);
        List<ProfileResponse> profileList = reactionPage
                .toList()
                .stream()
                .map(reaction -> profileRepository.findFiendProfileByAccountId(
                        reaction.getAccount().getId()).orElse(null)
                )
                .collect(Collectors.toList());
        return new PageResponse<>(
                profileList,
                reactionPage.getTotalElements(),
                pageable.getPageSize()
        );
    }

    @Override
    public PageResponse<CommentResponse> listCommentPost(Long id, PageRequest pageRequest, Account itMe) {
        Pageable pageable = PageUtils.toPageable(pageRequest);
        Post post = checkPostExists(id);
        Page<Comment> commentPage = commentRepository.findAllByCommentPostId(post.getId(), pageable);
        List<CommentResponse> commentResponses = commentPage
                .toList()
                .stream()
                .map(comment -> {
                    CommentResponse commentResponse = new CommentResponse();
                    commentResponse.setContent(comment.getContent());
                    commentResponse.setFiles(fileRepository.findAllByCommentFileId(comment.getId()));
                    commentResponse.setProfile(profileRepository.findFiendProfileByAccountId(comment.getAccount().getId()).orElse(null));
                    return commentResponse;
                }).collect(Collectors.toList());
        return new PageResponse<>(
                commentResponses,
                commentPage.getTotalElements(),
                pageable.getPageSize()
        );
    }

    @Override
    public Void reactionComment(Long id, ReactionRequest reactionRequest, Account itMe) {
        Comment comment = checkCommentExists(id);
        Timestamp currentTime = DateUtils.currentTimestamp();
        Reaction reaction = new Reaction();
        reaction.setAccount(itMe);
        reaction.setCreateAt(currentTime);
        reaction.setType(reactionRequest.getType());
        reaction = reactionRepository.save(reaction);
        CommentReaction commentReaction = new CommentReaction();
        commentReaction.setReaction(reaction);
        commentReaction.setComment(comment);
        commentReactionRepository.save(commentReaction);
        return null;
    }

    @Override
    public Void commentReply(Long id, CommentPostRequest commentPostRequest, Account itMe) {
        Comment comment = checkCommentExists(id);
        Comment commentReply = savedComment(commentPostRequest, itMe);
        CommentReply savedComment = new CommentReply();
        savedComment.setComment(comment);
        savedComment.setCommentReply(commentReply);
        commentReplyRepository.save(savedComment);
        return null;
    }

    private Comment checkCommentExists(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (!commentOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.REACT_COMMENT_NOT_EXISTS);
        }
        return commentOptional.get();
    }

    private Post checkPostExists(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.POST_NOT_EXISTS);
        }
        return optionalPost.get();
    }

    private Comment savedComment(CommentPostRequest commentPostRequest, Account account) {
        Timestamp currentTime = DateUtils.currentTimestamp();
        Comment comment = new Comment();
        comment.setContent(commentPostRequest.getContent());
        comment.setCreateAt(currentTime);
        comment.setAccount(account);
        comment = commentRepository.save(comment);
        Comment finalComment = comment;
        commentPostRequest.getFiles().forEach(strFile -> {
            File file = new File();
            file.setPath(strFile);
            file.setCreateAt(currentTime);
            file = fileRepository.save(file);
            CommentFile commentFile = new CommentFile();
            commentFile.setComment(finalComment);
            commentFile.setFile(file);
            commentFileRepository.save(commentFile);
        });
        return finalComment;
    }
}