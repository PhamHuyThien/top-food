package com.datn.topfood.data.repository.custom.impl;

import java.util.List;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.AccountFollow;
import com.datn.topfood.data.model.Favorite;
import com.datn.topfood.data.model.Food;
import com.datn.topfood.data.model.Post;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.dto.request.PageRequest;

public interface StoreCustomRepository {

	List<Food> searchFoods(String foodName,String tagName,String storeName,PageRequest pageRequest);
	Long countFoodsSearch(String foodName,String tagName,String storeName);
	
	List<Post> newFeed(Integer city,List<Favorite> favorite,PageRequest pageRequest);
	Long newFeedSize(Integer city,List<Favorite> favorite,PageRequest pageRequest);
	
	List<Post> postHastag(List<Favorite> favorite,PageRequest pageRequest);
	List<Post> postLike(PageRequest pageRequest);
	List<Post> postFollow(List<AccountFollow> accountFollow, PageRequest pageRequest);
}
