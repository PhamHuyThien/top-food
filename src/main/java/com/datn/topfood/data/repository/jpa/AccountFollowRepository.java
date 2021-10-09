package com.datn.topfood.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.AccountFollow;

public interface AccountFollowRepository extends JpaRepository<AccountFollow, Long>{
	
	@Query("select count(al) from AccountFollow as al where al.profile.id = ?1")
	long countFollowOfProfile(Long storeProfileId);
}
