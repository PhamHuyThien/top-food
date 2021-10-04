package com.datn.topfood.controllers;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.PageResponse;
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
    ResponseEntity<Response<FriendProfileResponse>> friendProfile(@PathVariable Long accountId) {
        return ResponseEntity.ok(new Response<FriendProfileResponse>(true, Message.OTHER_SUCCESS,
                accountService.getFiendProfileByAccountId(accountId)));
    }

    @Operation(description = "API gửi lời mời kết bạn")
    @PostMapping("/send-friend-invitations")
    ResponseEntity<Response<Boolean>> sendFriendInvitations(
            @RequestBody SendFriendInvitationsRequest friendInvitationsRequest) {
        return ResponseEntity.ok(new Response<Boolean>(true, Message.OTHER_SUCCESS,
                accountService.sendFriendInvitations(friendInvitationsRequest)));
    }

    @Operation(description = "API chặn bạn bè")
    @PostMapping("/block-friend")
    ResponseEntity<Response<Boolean>> blockFriend(@RequestBody BlockFriendRequest blockFriendRequest) {
        return ResponseEntity.ok(new Response<Boolean>(true, Message.OTHER_SUCCESS
                , accountService.blockFriend(blockFriendRequest)));
    }

    @Operation(description = "API phản hồi lời mời kết bạn")
    @PostMapping("/reply-friend")
    ResponseEntity<Response<Boolean>> replyFriend(
            @RequestBody ReplyInvitationFriendRequest replyInvitationFriendRequest) {
        return ResponseEntity.ok(new Response<Boolean>(true, Message.OTHER_SUCCESS
                , accountService.replyFriend(replyInvitationFriendRequest)));
    }

    @Operation(description = "API Lấy danh sách bạn bè")
    @GetMapping("/list-friends")
    ResponseEntity<PageResponse<FriendProfileResponse>> getListFriends(PageRequest pageRequest) {
        return ResponseEntity.ok(accountService.getListFriends(pageRequest));
    }

    @Operation(description = "API danh sách lời mời kết bạn")
    @GetMapping("/list-friends-request")
    ResponseEntity<PageResponse<FriendProfileResponse>> getListFriendsRequest(PageRequest pageRequest) {
        return ResponseEntity.ok(accountService.getListFriendsRequest(pageRequest));
    }

    @Operation(description = "API đổi mật khẩu")
    @PostMapping("/change-password")
    ResponseEntity<Response<?>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        accountService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

}