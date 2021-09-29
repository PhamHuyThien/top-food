package com.datn.topfood.controllers.controller;

import com.datn.topfood.controllers.api.LoginController;
import com.datn.topfood.dto.request.LoginRequest;
import com.datn.topfood.dto.response.LoginResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.AuthService;
import com.datn.topfood.util.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginControllerImpl implements LoginController {
    @Autowired
    AuthService authService;

    @Override
    public ResponseEntity<Response<LoginResponse>> loginWithUsername(LoginRequest loginRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, authService.loginWithUsername(loginRequest)));
    }
}
