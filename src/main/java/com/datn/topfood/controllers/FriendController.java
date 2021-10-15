package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.BlockFriendRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.RemoveFriendRequest;
import com.datn.topfood.dto.request.ReplyInvitationFriendRequest;
import com.datn.topfood.dto.request.SendFriendInvitationsRequest;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.FriendsService;
import com.datn.topfood.util.constant.Message;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    FriendsService friendsService;

    @Operation(description = "API gửi lời mời kết bạn")
    @PostMapping("/send-friend-invitations")
    public ResponseEntity<Response<?>> sendFriendInvitations(
            @RequestBody SendFriendInvitationsRequest friendInvitationsRequest) {
        friendsService.sendFriendInvitations(friendInvitationsRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API chặn bạn bè")
    @PostMapping("/block-friend")
    public ResponseEntity<Response<?>> blockFriend(@RequestBody BlockFriendRequest blockFriendRequest) {
        friendsService.blockFriend(blockFriendRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API phản hồi lời mời kết bạn")
    @PostMapping("/reply-friend")
    public ResponseEntity<Response<?>> replyFriend(
            @RequestBody ReplyInvitationFriendRequest replyInvitationFriendRequest) {
        friendsService.replyFriend(replyInvitationFriendRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API Lấy danh sách bạn bè")
    @GetMapping("/list-friends")
    public ResponseEntity<PageResponse<ProfileResponse>> getListFriends(PageRequest pageRequest) {
        return ResponseEntity.ok(friendsService.getListFriends(pageRequest));
    }

    @Operation(description = "API danh sách lời mời kết bạn")
    @GetMapping("/list-friends-request")
    public ResponseEntity<PageResponse<ProfileResponse>> getListFriendsRequest(PageRequest pageRequest) {
        return ResponseEntity.ok(friendsService.getListFriendsRequest(pageRequest));
    }
    
    @Operation(description = "API hủy kết bạn")
    @DeleteMapping("/remove-friend/{friendPhoneNumber}")
    public ResponseEntity<Response<?>> removeFriend(@PathVariable("friendPhoneNumber") String friendPhoneNumber) {
        friendsService.removeFriend(friendPhoneNumber);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }
}
