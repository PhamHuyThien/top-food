package com.datn.topfood.dto.response;

import java.util.List;

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
	List<FileRequest> files;
}
