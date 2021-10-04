package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.dto.response.PageResponse;

public interface AccountService {
    FriendProfileResponse getFiendProfileByAccountId(Long id);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void active(ActiveRequest activeRequest);
}
