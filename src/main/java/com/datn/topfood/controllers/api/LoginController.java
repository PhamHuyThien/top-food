package com.datn.topfood.controllers.api;

import com.datn.topfood.dto.request.LoginRequest;
import com.datn.topfood.dto.response.LoginResponse;
import com.datn.topfood.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginController {

    @Operation(description = "API đăng nhập bằng tài khoản")
    @PostMapping("/login-with-username")
    ResponseEntity<Response<LoginResponse>> loginWithUsername(@RequestBody LoginRequest loginRequest);

}
