package com.datn.topfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchFoodsRequest {

	public String foodName;
	public String tagName;
	public Double minPrice;
	public Double maxPrice;
}
