package com.datn.topfood.data.repository.custom.impl;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.FriendProfileResponse;
import java.util.List;

public interface FriendshipCustomRepository {

    List<FriendProfileResponse> findByListFriends(long profileId, PageRequest pageRequest);
    Long getTotalProfile(long profileId);
}
