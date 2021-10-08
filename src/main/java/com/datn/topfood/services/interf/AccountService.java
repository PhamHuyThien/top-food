package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.ProfileResponse;

public interface AccountService {

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void active(ActiveRequest activeRequest);

    void checkOtp(long profileId, String otp);
}
