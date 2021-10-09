package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.response.FoodDetailResponse;

public interface StoreProfileServic {

	void createFood(FoodRequest foodRequest);
	FoodDetailResponse foodDetail(Long foodId);
	
}
