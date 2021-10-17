package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.AccountResponse;
import com.datn.topfood.dto.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.AccountService;
import com.datn.topfood.util.constant.Message;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Operation(description = "API đổi mật khẩu")
    @PutMapping("/change-password")
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
    @GetMapping("")
    public PageResponse<AccountResponse> searchByPhoneNumber(@RequestParam String phoneNumber,PageRequest pageRequest){
        return accountService.searchByPhoneNumber(phoneNumber,pageRequest);
    }
    @PutMapping("/enable/{id}")
    public void updateEnable(@PathVariable("id") Long id){
        accountService.updateEnable(id);
    }
    @DeleteMapping("/admin/{id}")
    public void deleteAccount(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
    }
    @PutMapping("/admin/active/{id}")
    public void adminActiveAccount(@PathVariable("id") Long id){
        accountService.updateActive(id);
    }
    @PutMapping("/admin/update-role/{id}")
    public void updateRole(@PathVariable("id") Long id){
        accountService.updateRole(id);
    }
    @PostMapping("/admin")
    public void adminCreateAccount(@RequestBody RegisterRequest request){
        accountService.createAccount(request);
    }
    @PutMapping("/admin/reset-password/{id}")
    public void adminResetPassword(@PathVariable("id") Long id){
        accountService.resetPassword(id);
    }
}