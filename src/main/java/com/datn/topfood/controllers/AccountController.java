package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.services.interf.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.AccountService;
import com.datn.topfood.util.constant.Message;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {
    @Autowired
    ProfileService profileService;
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

    @PostMapping(path = "")
    public void createProfile(@RequestBody RegisterRequest create){
        profileService.createProfile(create);
    }
    @PutMapping("/{id}")
    public ProfileResponse updateProfile(@RequestBody ProfileRequest update, @PathVariable Long id){
        return profileService.updateProfile(update,id);
    }
    @GetMapping("/{id}")
    public ProfileResponse findById(@PathVariable Long id){
        return profileService.findById(id);
    }
    @GetMapping(path = "")
    public List<ProfileResponse> search(@RequestParam String name){
        return profileService.SearchByNameAndPhone(name);
    }
}