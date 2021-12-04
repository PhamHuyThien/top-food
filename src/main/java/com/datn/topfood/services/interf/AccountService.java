package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.*;
import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;

import java.sql.Timestamp;

public interface AccountService {

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void active(ActiveRequest activeRequest);

    void checkOtp(long profileId, String otp);

    PageResponse<AccountResponse> searchByPhoneNumber(String phoneNumber, AccountRole role,AccountStatus status, PageRequest request);

    void updateEnable(Long id);

    void createAccount(CreateUser request);

    void updateRole(UpdateRoleRequest request, Long id);

    void updateActive(Long id);

    void deleteAccount(Long id);

    void resetPassword(Long id);

    AccountDetailResponse getAccountById(Long id);

    TotalAccountUser getTotalAccountUser();

    TotalAccountStore getTotalAccountStore();
}
