package com.datn.topfood.services.interf;

import com.datn.topfood.dto.response.FriendProfileResponse;

public interface FriendServic {

	FriendProfileResponse getFiendProfileByAccountId(Long id);
	
}
