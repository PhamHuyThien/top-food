package com.datn.topfood.controllers;

import com.datn.topfood.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datn.topfood.data.model.Post;
import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.PostRequest;
import com.datn.topfood.dto.request.SearchFoodsRequest;
import com.datn.topfood.services.interf.StoreProfileServic;
import com.datn.topfood.util.constant.Message;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/store-profile")
public class StoreProfileController {

    @Autowired
    StoreProfileServic profileServic;

    @Operation(description = "API thêm món ăn")
    @PostMapping("/food/create")
    public ResponseEntity<Response<Void>> createFood(@RequestBody FoodRequest foodRequest) {
        profileServic.createFood(foodRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API xem chi tiết món ăn")
    @GetMapping("/food/{foodId}")
    public ResponseEntity<Response<FoodDetailResponse>> foodDetail(@PathVariable Long foodId) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, profileServic.foodDetail(foodId)));
    }

    @Operation(description = "API follow cửa hàng")
    @PostMapping("/follow/{storeProfileId}")
    public ResponseEntity<Response<Void>> follow(@PathVariable Long storeProfileId) {
        profileServic.followStore(storeProfileId);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API chi tiết profile cửa hàng")
    @GetMapping("/wall/{storeProfileId}")
    public ResponseEntity<Response<StoreWallResponse>> storeProfile(@PathVariable Long storeProfileId) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, profileServic.storeProfile(storeProfileId)));
    }

    @Operation(description = "API danh sách cửa hàng follow của account")
    @GetMapping("/list-store-follow")
    public ResponseEntity<Response<PageResponse<StoreWallResponse>>> listStoreFollow(PageRequest pageRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, profileServic.listStoreFollow(pageRequest)));
    }

    @Operation(description = "API account follow của cửa hàng")
    @GetMapping("/list-follow-store")
    public ResponseEntity<Response<PageResponse<SimpleAccountResponse>>> listFollowStore(PageRequest pageRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, profileServic.listFollowStore(pageRequest)));
    }

    @Operation(description = "API danh sách món ăn của cửa hàng")
    @GetMapping("/list-food")
    public ResponseEntity<Response<PageResponse<FoodDetailResponse>>> listFood(PageRequest pageRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, profileServic.listFood(pageRequest)));
    }

    @Operation(description = "API hủy follow cửa hàng")
    @DeleteMapping("/un-follow/{storeProfileId}")
    public ResponseEntity<Response<Void>> unFollowStore(@PathVariable Long storeProfileId) {
        profileServic.unFollowStore(storeProfileId);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API xóa món ăn")
    @DeleteMapping("/food/delete/{foodId}")
    public ResponseEntity<Response<Void>> deleteFood(@PathVariable Long foodId) {
        profileServic.deleteFood(foodId);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API sửa món ăn")
    @PutMapping("/food/update")
    public ResponseEntity<Response<FoodDetailResponse>> updateFood(@RequestBody FoodRequest foodRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, profileServic.updateFood(foodRequest)));
    }

    @Operation(description = "API thêm bài viết")
    @PostMapping("/post/create")
    public ResponseEntity<Response<PostResponse>> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, profileServic.createPost(postRequest)));
    }

    @Operation(description = "API xóa bài viết")
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<Response<Void>> deletePost(@PathVariable("postId") Long id) {
        profileServic.deletePost(id);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }
    
    @Operation(description = "API chi tiêt bài viết")
    @GetMapping("/post/detail/{postId}")
    public ResponseEntity<Response<Post>> detailPost(@PathVariable("postId") Long id) {
        profileServic.deletePost(id);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }
    
    @Operation(description = "API sửa bài viết")
    @PutMapping("/post/update")
    public ResponseEntity<Response<PostResponse>> updatePost(@RequestBody PostRequest postRs) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, profileServic.updatePost(postRs)));
    }
    
    @Operation(description = "API lấy danh sách bài viết")
    @GetMapping("/list-post")
    public ResponseEntity<PageResponse<PostResponse>> getListPost(PageRequest pageRequest) {
        return ResponseEntity.ok(profileServic.getListPost(pageRequest));
    }
    
    @Operation(description = "API tìm kiếm món ăn")
    @GetMapping("/search/food")
    public ResponseEntity<PageResponse<FoodDetailResponse>> searchFoods(@RequestBody SearchFoodsRequest foodsRequest,PageRequest pageRequest) {
        return ResponseEntity.ok(profileServic.searchFoods(foodsRequest,pageRequest));
    }
}