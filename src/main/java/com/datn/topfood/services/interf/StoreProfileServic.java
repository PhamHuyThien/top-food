package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Food;
import com.datn.topfood.dto.request.FoodRequest;

public interface StoreProfileServic {

	void createFood(FoodRequest foodRequest);
	Food foodDetail(Long foodId);
	
}
