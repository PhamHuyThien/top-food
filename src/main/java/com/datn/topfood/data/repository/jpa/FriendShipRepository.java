package com.datn.topfood.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.FriendShip;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long>{
	
	@Query("select fs from FriendShip as fs where fs.accountRequest.username = ?1 and fs.accountAddressee.username = ?2"
			+ "or (fs.accountRequest.username = ?2 and fs.accountAddressee.username = ?1)")
	FriendShip findFriendShipForBlock(String usernameRequestPerson,String usernameBlockPerson);
}
