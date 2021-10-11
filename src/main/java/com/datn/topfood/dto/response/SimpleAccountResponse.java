package com.datn.topfood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleAccountResponse {

	private String cover;
	private String avatar;
	private String bio;
	private String address;
	private String name;
	
}
