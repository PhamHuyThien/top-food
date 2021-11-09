package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.request.ReactPostRequest;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.ReactService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<Response<Void>> reactPost(@RequestParam Long id, @RequestBody ReactPostRequest reactPostRequest){
        return ResponseEntity.ok(new Response<>(reactService.reactPost(id, reactPostRequest, itMe())));
    }
}
