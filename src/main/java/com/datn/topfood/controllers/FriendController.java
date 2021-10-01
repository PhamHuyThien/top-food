package com.datn.topfood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
