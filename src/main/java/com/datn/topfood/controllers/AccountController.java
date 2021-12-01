package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.*;
import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datn.topfood.services.interf.AccountService;
import com.datn.topfood.util.constant.Message;

import io.swagger.v3.oas.annotations.Operation;

import java.sql.Timestamp;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Operation(description = "API đổi mật khẩu")
    @PutMapping("/change-password")
    public ResponseEntity<Response<Void>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        accountService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API kích hoạt tài khoản")
    @PostMapping("active")
    public ResponseEntity<Response<Void>> active(@RequestBody ActiveRequest activeRequest) {
        accountService.active(activeRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @GetMapping("")
    public PageResponse<AccountResponse> searchByPhoneNumber(@RequestParam String phoneNumber,@RequestParam AccountRole role, PageRequest pageRequest) {
        return accountService.searchByPhoneNumber(phoneNumber,role, pageRequest);
    }

    @PutMapping("/enable/{id}")
    public void updateEnable(@PathVariable("id") Long id) {
        accountService.updateEnable(id);
    }

    @DeleteMapping("/admin/{id}")
    public void deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
    }

    @PutMapping("/admin/active/{id}")
    public void adminActiveAccount(@PathVariable("id") Long id) {
        accountService.updateActive(id);
    }

    @PutMapping("/admin/update-role/{id}")
    public void updateRole(@PathVariable("id") Long id, @RequestBody UpdateRoleRequest request) {
        accountService.updateRole(request, id);
    }

    @PostMapping("/admin")
    public void adminCreateAccount(@RequestBody CreateUser request) {
        accountService.createAccount(request);
    }

    @PutMapping("/admin/reset-password/{id}")
    public void adminResetPassword(@PathVariable("id") Long id) {
        accountService.resetPassword(id);
    }

    @GetMapping("/admin/{id}")
    public AccountDetailResponse getAccountById(@PathVariable("id") Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/total/user")
    public TotalAccountUser totalAccountUser() {
        return accountService.getTotalAccountUser();
    }
    @GetMapping("/total/store")
    public TotalAccountStore totalAccountStore() {
        return accountService.getTotalAccountStore();
    }
}