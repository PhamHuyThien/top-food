package com.datn.topfood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.StoreProfileServic;
import com.datn.topfood.util.constant.Message;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin
@RequestMapping("/store-profile")
public class StoreProfileController {

	@Autowired
	StoreProfileServic profileServic;
	
	@Operation(description = "API thêm món ăn")
	@PostMapping("/food/create")
	public ResponseEntity<?> createFood(@RequestBody FoodRequest foodRequest){
		profileServic.createFood(foodRequest);
		return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
	}
	
	@Operation(description = "API xem chi tiết món ăn")
	@GetMapping("/food/{foodId}")
	public ResponseEntity<?> foodDetail(@PathVariable Long foodId){
		return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS,profileServic.foodDetail(foodId)));
	}
	
	@Operation(description = "API follow cửa hàng")
	@PostMapping("/follow/{storeProfileId}")
	public ResponseEntity<?> follow(@PathVariable Long storeProfileId){
		profileServic.followStore(storeProfileId);
		return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
	}
	
	@Operation(description = "API chi tiết profile cửa hàng")
	@GetMapping("/wall/{storeProfileId}")
	public ResponseEntity<?> storeProfile(@PathVariable Long storeProfileId){
		return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS,profileServic.storeProfile(storeProfileId)));
	}
}
