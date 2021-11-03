package com.datn.topfood.data.repository.custom.impl;

import java.util.List;

import com.datn.topfood.data.model.Food;
import com.datn.topfood.dto.request.PageRequest;

public interface StoreCustomRepository {

	List<Food> searchFoods(String foodName,String tagName,Double minPrice
			,Double maxPrice,PageRequest pageRequest);
	Long countFoodsSearch(String foodName,String tagName,Double minPrice,Double maxPrice);
}
