package com.datn.topfood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn.topfood.dto.request.BlockFriendRequest;
import com.datn.topfood.dto.request.SendFriendInvitationsRequest;
import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.FriendServic;
import com.datn.topfood.util.constant.Message;

@RestController
@RequestMapping("/friend")
public class FriendController {

	@Autowired
	FriendServic friendServic;

	@GetMapping("/profile/{accountId}")
	ResponseEntity<Response<FriendProfileResponse>> friendProfile(@PathVariable Long accountId) {
		return ResponseEntity.ok(new Response<FriendProfileResponse>(true, Message.OTHER_SUCCESS,
				friendServic.getFiendProfileByAccountId(accountId)));
	}

	@PostMapping("/send-friend-invitations")
	ResponseEntity<Response<String>> sendFriendInvitations(
			@RequestBody SendFriendInvitationsRequest friendInvitationsRequest) {
		friendServic.sendFriendInvitations(friendInvitationsRequest);
		return ResponseEntity.ok(new Response<String>(true, Message.OTHER_SUCCESS));
	}

	@PostMapping("/block-friend")
	ResponseEntity<Response<String>> blockFriend(@RequestBody BlockFriendRequest blockFriendRequest) {
		friendServic.blockFriend(blockFriendRequest);
		return ResponseEntity.ok(new Response<String>(true, Message.OTHER_SUCCESS));
	}

}
