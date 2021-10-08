package com.datn.topfood.data.repository.custom.impl;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.ProfileResponse;
import java.util.List;

public interface FriendshipCustomRepository {

    List<ProfileResponse> findByListFriends(long profileId, PageRequest pageRequest);
    Long getTotalProfile(long profileId);
}
