package com.datn.topfood.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datn.topfood.data.model.FriendShip;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FriendShipRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.BlockFriendRequest;
import com.datn.topfood.dto.request.SendFriendInvitationsRequest;
import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.services.interf.FriendServic;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.FriendShipStatus;

@Service
public class FriendServicImpl implements FriendServic {

	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	FriendShipRepository friendShipRepository;

	@Override
	public FriendProfileResponse getFiendProfileByAccountId(Long id) {
		return profileRepository.findFiendProfileByAccountId(id);
	}

	@Override
	public void sendFriendInvitations(SendFriendInvitationsRequest friendInvitationsRequest) {
		FriendShip friendShip = new FriendShip();

		friendShip.setStatus(FriendShipStatus.SENDING);
		friendShip.setAccountRequest(accountRepository.findByUsername(friendInvitationsRequest.getUsernameRequest()));
		friendShip
				.setAccountAddressee(accountRepository.findByPhoneNumber(friendInvitationsRequest.getPhoneAddressee()));
		friendShip.setCreateAt(DateUtils.currentTimestamp());

		friendShip = friendShipRepository.save(friendShip);

	}

	@Override
	public void blockFriend(BlockFriendRequest blockFriendRequest) {
		// TODO Auto-generated method stub
		FriendShip friendShip =  friendShipRepository.findFriendShipForBlock(blockFriendRequest.getUsernameRequestPerson(), 
				blockFriendRequest.getUsernameBlockPerson());
		// nếu chưa là bạn bè sẽ từ chối block
		if(friendShip==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		
		friendShip.setStatus(FriendShipStatus.BLOCK);
		
		friendShipRepository.save(friendShip);
	}
}
