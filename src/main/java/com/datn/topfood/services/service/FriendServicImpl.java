package com.datn.topfood.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.services.interf.FriendServic;

@Service
public class FriendServicImpl implements FriendServic{

	@Autowired
	ProfileRepository profileRepository;
	
	@Override
	public FriendProfileResponse getFiendProfileByAccountId(Long id) {
		// TODO Auto-generated method stub
		return profileRepository.findFiendProfileByAccountId(id);
	}
}
