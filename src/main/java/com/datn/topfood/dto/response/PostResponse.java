package com.datn.topfood.dto.response;

import java.util.Collection;
import java.util.List;

import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.model.Tag;
import com.datn.topfood.dto.request.FileRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

	Long id;
	String content;
	List<String> files;
	List<TagResponse> tags;
	List<FoodResponse> foods;
	ProfileResponse profile;
	Long totalReaction;
	boolean isMyReaction;
}
