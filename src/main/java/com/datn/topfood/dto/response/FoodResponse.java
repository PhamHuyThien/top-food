package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Reaction;
import com.datn.topfood.dto.request.FileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodResponse {
    Long id;
    String name;
    Double price;
    String content;
    List<String> files;
    String storeName;
    Long totalReaction;
    boolean reactionMe;


	public FoodResponse(Long id, String name, Double price, String content, List<String> files, String storeName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.content = content;
		this.files = files;
		this.storeName = storeName;
	}
    
}
