package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.BlockFriendRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.RemoveFriendRequest;
import com.datn.topfood.dto.request.ReplyInvitationFriendRequest;
import com.datn.topfood.dto.request.SendFriendInvitationsRequest;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.PageResponse;

public interface FriendsService {

    void sendFriendInvitations(SendFriendInvitationsRequest friendInvitationsRequest);

    void blockFriend(BlockFriendRequest blockFriendRequest);

    void replyFriend(ReplyInvitationFriendRequest replyInvitationFriendRequest);

    PageResponse<ProfileResponse> getListFriends(PageRequest pageRequest);

    PageResponse<ProfileResponse> getListFriendsRequest(PageRequest pageRequest);
    
    void removeFriend(String removeFriendRequest);
}
