package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.AccountService;
import com.datn.topfood.util.constant.Message;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Operation(description = "API xem thông tin profile bạn bè")
    @GetMapping("/profile/{accountId}")
    public ResponseEntity<Response<FriendProfileResponse>> friendProfile(@PathVariable Long accountId) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS,
                accountService.getFiendProfileByAccountId(accountId)));
    }

    @Operation(description = "API đổi mật khẩu")
    @PostMapping("/change-password")
    public ResponseEntity<Response<?>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        accountService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API kích hoạt tài khoản")
    @PostMapping("active")
    public ResponseEntity<Response<?>> active(@RequestBody ActiveRequest activeRequest) {
        accountService.active(activeRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }
}