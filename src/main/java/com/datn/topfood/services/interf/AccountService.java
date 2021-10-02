package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.BlockFriendRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ReplyInvitationFriendRequest;
import com.datn.topfood.dto.request.SendFriendInvitationsRequest;
import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.dto.response.PageResponse;

public interface AccountService {

	FriendProfileResponse getFiendProfileByAccountId(Long id);
	void sendFriendInvitations(SendFriendInvitationsRequest friendInvitationsRequest);
	void blockFriend(BlockFriendRequest blockFriendRequest);
	void replyFriend(ReplyInvitationFriendRequest replyInvitationFriendRequest);
	PageResponse<FriendProfileResponse> getListFriends(PageRequest pageRequest);
}
