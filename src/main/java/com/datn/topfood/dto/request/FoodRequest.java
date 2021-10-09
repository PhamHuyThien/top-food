package com.datn.topfood.dto.request;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest {

	Long id;
	Double price;
	String name;
	String content;
	
	ArrayList<FileRequest> files;
	
}
