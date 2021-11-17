package com.datn.topfood.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreWallResponse {

	Long storeId;
	long follower;
	String cover;
	String avatar;
	String bio;
	String address;
	String name;
}
