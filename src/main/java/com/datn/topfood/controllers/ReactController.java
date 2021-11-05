package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/react")
public class ReactController extends BaseService {
    @Autowired
    ReactService reactService;

    public ResponseEntity<Response<Void>> commentPost(@RequestBody CommentPostRequest commentPostRequest) {
        return ResponseEntity.ok(new Response<>(reactService.commentPost(commentPostRequest, itMe())));
    }
}
