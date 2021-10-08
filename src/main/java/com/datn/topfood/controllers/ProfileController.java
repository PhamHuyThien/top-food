package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.ProfileService;
import com.datn.topfood.util.constant.Message;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/profiles")
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @Operation(description = "API xem thông tin profile")
    @GetMapping("/{accountId}")
    public ResponseEntity<Response<ProfileResponse>> friendProfile(@PathVariable Long accountId) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS,
                profileService.getFiendProfileByAccountId(accountId)));
    }

    @Operation(description = "API cập nhật profile")
    @PutMapping("/update")
    public ResponseEntity<Response<?>> updateProfile(@RequestBody ProfileRequest profileRequest) {
        profileService.updateProfile(profileRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API tìm kiếm bạn bè qua tên hoặc số điện thoại")
    @GetMapping(path = "/search")
    public ResponseEntity<PageResponse<ProfileResponse>> search(String search, PageRequest pageRequest) {
        return ResponseEntity.ok(profileService.search(search, pageRequest));
    }
}
