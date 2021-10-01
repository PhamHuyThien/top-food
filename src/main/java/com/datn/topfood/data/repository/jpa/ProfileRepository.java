package com.datn.topfood.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.Profile;
import com.datn.topfood.dto.response.FriendProfileResponse;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

	@Query("select new com.datn.topfood.dto.response.FriendProfileResponse(account.phoneNumber,account.email,profile)"
			+ " from Account as account join account.profile as profile where account.id = ?1")
	FriendProfileResponse findFiendProfileByAccountId(Long id);
}
