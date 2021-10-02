package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.BlockFriendRequest;
import com.datn.topfood.dto.request.ReplyInvitationFriendRequest;
import com.datn.topfood.dto.request.SendFriendInvitationsRequest;
import com.datn.topfood.dto.response.FriendProfileResponse;

public interface AccountService {

	FriendProfileResponse getFiendProfileByAccountId(Long id);
	void sendFriendInvitations(SendFriendInvitationsRequest friendInvitationsRequest);
	void blockFriend(BlockFriendRequest blockFriendRequest);
	void replyFriend(ReplyInvitationFriendRequest replyInvitationFriendRequest);
	
}
