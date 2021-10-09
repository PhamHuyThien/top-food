package com.datn.topfood.services.interf;

import java.util.List;

import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.response.FoodDetailResponse;
import com.datn.topfood.dto.response.StoreWallResponse;

public interface StoreProfileServic {

	void createFood(FoodRequest foodRequest);
	FoodDetailResponse foodDetail(Long foodId);
	void followStore(Long storeProfileId);
	StoreWallResponse storeProfile(Long storeProfileId);
	List<StoreWallResponse> listStoreFollow();
	void unFollowStore(Long storeId);
}
