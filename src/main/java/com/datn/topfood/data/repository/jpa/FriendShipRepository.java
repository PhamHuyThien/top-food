package com.datn.topfood.data.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.FriendShip;
import org.springframework.data.repository.query.Param;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {

    @Query("select fs from FriendShip as fs where (fs.accountRequest.username = ?1 and fs.accountAddressee.username = ?2)"
            + "or (fs.accountRequest.username = ?2 and fs.accountAddressee.username = ?1)")
    FriendShip findFriendShipRelation(String username1, String username2);

    @Query("select fs from FriendShip as fs where fs.accountAddressee.username = ?1 and fs.accountRequest.username = ?2")
    FriendShip findFriendByReplyPersonToRequestPerson(String replyUsername, String requestUsername);

    @Query("select fs from FriendShip fs where fs.accountAddressee.id = ?1 and fs.status = 'SENDING'")
    Page<FriendShip> findByAccountAddresseeAndStatus(long profileId, Pageable pageable);

    @Query("SELECT fs FROM FriendShip fs " +
            "WHERE (fs.accountAddressee.id = ?1  OR fs.accountRequest.id = ?2) AND fs.blockBy.id = ?3 ")
    Page<FriendShip> getAllByAccountAddOrAccountReqAndBlockAt(Long accountIdMe, Long accountIdMe2, Long accountIdMe3, Pageable pageable);

}
