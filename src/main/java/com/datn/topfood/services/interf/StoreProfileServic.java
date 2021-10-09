package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.FoodDetailResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.StoreWallResponse;

public interface StoreProfileServic {

	void createFood(FoodRequest foodRequest);
	FoodDetailResponse foodDetail(Long foodId);
	void followStore(Long storeProfileId);
	StoreWallResponse storeProfile(Long storeProfileId);
	PageResponse<StoreWallResponse> listStoreFollow(PageRequest pageRequest);
	void unFollowStore(Long storeId);
	void deleteFood(Long foodId);
}
