package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.AccountOtp;
import com.datn.topfood.data.repository.custom.impl.FriendshipCustomRepository;
import com.datn.topfood.data.repository.jpa.AccountOtpRepository;
import com.datn.topfood.dto.request.*;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.util.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FriendShipRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.services.interf.AccountService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.constant.Message;

@Service
public class AccountServiceImpl extends BaseService implements AccountService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FriendShipRepository friendShipRepository;
    @Autowired
    FriendshipCustomRepository friendshipCustomRepository;
    @Autowired
    AccountOtpRepository accountOtpRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public FriendProfileResponse getFiendProfileByAccountId(Long id) {
        return profileRepository.findFiendProfileByAccountId(id);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Account itMe = itMe();
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), itMe.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_OLD_PASSWORD_WRONG);
        }
        itMe.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        accountRepository.save(itMe);
    }

    @Override
    @Transactional
    public void active(String otp) {
        Account itMe = itMe();
        if (itMe.getStatus() != AccountStatus.WAIT_ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_IS_ACTIVE);
        }
        AccountOtp accountOtp = accountOtpRepository.findByAccountId(itMe.getId()).orElse(null);
        if (accountOtp == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_OTP_NOT_EXISTS);
        }
        if (!accountOtp.getOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_OTP_WRONG);
        }
        accountOtpRepository.delete(accountOtp);
        itMe.setStatus(AccountStatus.ACTIVE);
        itMe.setUpdateAt(DateUtils.currentTimestamp());
        accountRepository.save(itMe);
    }
}