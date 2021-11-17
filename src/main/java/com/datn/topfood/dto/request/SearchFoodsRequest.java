package com.datn.topfood.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchFoodsRequest {

	public String foodName;
	public String storeName;
	public String tagName;
}
