package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ReactionRequest;
import com.datn.topfood.dto.response.CommentResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.ReactService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/react")
public class ReactController extends BaseService {
    @Autowired
    ReactService reactService;

    @Operation(description = "API comment vào bài viết.")
    @PostMapping("/comment-post")
    public ResponseEntity<Response<Void>> commentPost(@RequestParam Long id, @RequestBody CommentPostRequest commentPostRequest) {
        return ResponseEntity.ok(new Response<>(reactService.commentPost(id, commentPostRequest, itMe())));
    }

    @Operation(description = "API thả tim bài viết.")
    @PostMapping("/reaction-post")
    public ResponseEntity<Response<Void>> reactPost(@RequestParam Long id, @RequestBody ReactionRequest reactPostRequest){
        return ResponseEntity.ok(new Response<>(reactService.reactPost(id, reactPostRequest, itMe())));
    }

    @Operation(description = "API danh sách lượt tim.")
    @GetMapping("/list-reaction-post")
    public ResponseEntity<PageResponse<ProfileResponse>> listReactionPost(@RequestParam Long id, PageRequest pageRequest){
        return ResponseEntity.ok(reactService.listReactionPost(id, pageRequest, itMe()));
    }

    @Operation(description = "API danh sách bình luận.")
    @GetMapping("/list-comment-post")
    public ResponseEntity<PageResponse<CommentResponse>> listCommentPost(@RequestParam Long id, PageRequest pageRequest){
        return ResponseEntity.ok(reactService.listCommentPost(id, pageRequest, itMe()));
    }

    @Operation(description = "API thả tim bình luận.")
    @PostMapping("/reaction-comment")
    public ResponseEntity<Response<Void>> reactionComment(@RequestParam Long id, @RequestBody ReactionRequest reactionRequest){
        return ResponseEntity.ok(new Response<>(reactService.reactionComment(id, reactionRequest, itMe())));
    }
}
