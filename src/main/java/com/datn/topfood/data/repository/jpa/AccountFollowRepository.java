package com.datn.topfood.data.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.AccountFollow;

public interface AccountFollowRepository extends JpaRepository<AccountFollow, Long>{
	
	@Query("select count(al) from AccountFollow as al where al.profile.id = ?1")
	long countFollowOfProfile(Long storeProfileId);
	
	@Query("select al from AccountFollow as al where al.account.username = ?1")
	Page<AccountFollow> listStoreFollow(String username,Pageable pageable);
	
	@Query("select al from AccountFollow as al where al.account.username = ?1 and al.profile.id = ?2")
	Optional<AccountFollow> findByAccountUsernameAndProfileId(String username,Long ProfileId);
}
