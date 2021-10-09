package com.datn.topfood.dto.request;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest {

	Double price;
	String name;
	String content;
	
	ArrayList<File> files;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class File {
		String path;
		String type;
	}
}
