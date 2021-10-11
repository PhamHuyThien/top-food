package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.AccountResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;

public interface AccountService {

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void active(ActiveRequest activeRequest);

    void checkOtp(long profileId, String otp);
    PageResponse<AccountResponse> searchByPhoneNumber(String phoneNumber,PageRequest request);
    void updateEnable(Long id);
}
