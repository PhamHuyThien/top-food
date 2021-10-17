package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;

public interface FriendsService {

    void sendFriendInvitations(SendFriendInvitationsRequest friendInvitationsRequest);

    void blockFriend(BlockFriendRequest blockFriendRequest);

    void replyFriend(ReplyInvitationFriendRequest replyInvitationFriendRequest);

    PageResponse<ProfileResponse> getListFriends(PageRequest pageRequest);

    PageResponse<ProfileResponse> getListFriendsRequest(PageRequest pageRequest);
    
    void removeFriend(String removeFriendRequest);

    PageResponse<ProfileResponse> getListFriendBlock(PageRequest pageRequest);

    void unblockFriend(UnblockFriendRequest unblockFriendRequest);
}
