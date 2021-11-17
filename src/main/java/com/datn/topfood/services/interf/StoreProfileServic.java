package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Post;
import com.datn.topfood.dto.request.FoodReactionRequest;
import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.PostRequest;
import com.datn.topfood.dto.request.SearchFoodsRequest;
import com.datn.topfood.dto.response.FoodDetailResponse;
import com.datn.topfood.dto.response.FoodResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.PostResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.SimpleAccountResponse;
import com.datn.topfood.dto.response.StoreWallResponse;

public interface StoreProfileServic {

    void createFood(FoodRequest foodRequest);

    FoodDetailResponse foodDetail(Long foodId);

    void followStore(Long storeProfileId);

    StoreWallResponse storeProfile(Long storeProfileId);

    PageResponse<StoreWallResponse> listStoreFollow(PageRequest pageRequest);

    PageResponse<SimpleAccountResponse> listFollowStore(PageRequest pageRequest);

    void unFollowStore(Long storeId);

    void deleteFood(Long foodId);

    PageResponse<FoodDetailResponse> listFood(PageRequest pageRequest);

    FoodDetailResponse updateFood(FoodRequest foodRequest);

    PostResponse createPost(PostRequest postRequest);

    void deletePost(Long postId);
    
    PostResponse detailPost(Long id);
    
    PageResponse<PostResponse> getListPost(Long accountId,PageRequest pageRequest);
    
    PageResponse<PostResponse> getListPostAll(PageRequest pageRequest);
    
    PostResponse updatePost(PostRequest postRequest);
    
    PageResponse<FoodDetailResponse> searchFoods(SearchFoodsRequest foodsRequest,PageRequest pageRequest);
    
    PageResponse<FoodDetailResponse> searchFoodsSort(PageRequest pageRequest);
    
    PageResponse<PostResponse> searchPostByAddress(String address,PageRequest pageRequest);
    
    void foodReaction(FoodReactionRequest foodReactionRequest);
    
    void foodReactionDel(FoodReactionRequest foodReactionRequest);
    
    void addFoodHot(Long foodId);
}
