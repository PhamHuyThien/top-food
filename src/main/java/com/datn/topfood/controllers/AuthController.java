package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.LoginRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.LoginResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.AuthService;
import com.datn.topfood.util.constant.Message;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Operation(description = "API đăng nhập bằng tài khoản")
    @PostMapping("/login-with-username")
    public ResponseEntity<Response<LoginResponse>> loginWithUsername(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, authService.loginWithUsername(loginRequest)));
    }
    
    @Operation(description = "API đăng ký tài khoản")
    @PostMapping("/register")
    public ResponseEntity<Response<String>> register(@RequestBody RegisterRequest registerRequest) {
    	authService.insertAccount(registerRequest);
		return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS,null));
	}

}
