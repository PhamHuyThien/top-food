package com.datn.topfood.dto.response;

import java.util.List;

import com.datn.topfood.dto.request.FileRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDetailResponse {
	Long id;
	String name;
	Double price;
	String content;
	List<FileRequest> files;
}
