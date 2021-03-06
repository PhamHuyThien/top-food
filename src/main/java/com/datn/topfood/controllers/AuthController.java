package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.ForgotPasswordRequest;
import com.datn.topfood.dto.request.LoginRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.LoginResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.AuthService;
import com.datn.topfood.util.constant.Message;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response<Void>> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API quên mật khẩu")
    @PostMapping("/forgot-password")
    public ResponseEntity<Response<Void>> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API lấy OTP")
    @GetMapping("/get-otp")
    public ResponseEntity<Response<Void>> getOtp(String email) {
        authService.getOtp(email);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }
}
