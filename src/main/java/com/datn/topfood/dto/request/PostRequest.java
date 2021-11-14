package com.datn.topfood.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

	Long id;
	String content;
	List<String> files;
	Long[] tagIds;
	List<String> address;
	List<Long> foodIds;
}
