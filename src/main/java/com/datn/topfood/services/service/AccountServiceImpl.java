package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.AccountOtp;
import com.datn.topfood.data.repository.custom.impl.FriendshipCustomRepository;
import com.datn.topfood.data.repository.jpa.AccountOtpRepository;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FriendShipRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.ActiveRequest;
import com.datn.topfood.dto.request.ChangePasswordRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.AccountResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.AccountPro;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.AccountService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  public void active(ActiveRequest activeRequest) {
    Account itMe = itMe();
    if (itMe.getStatus() != AccountStatus.WAIT_ACTIVE) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_IS_ACTIVE);
    }
    checkOtp(itMe.getId(), activeRequest.getOtp());
    itMe.setStatus(AccountStatus.ACTIVE);
    itMe.setUpdateAt(DateUtils.currentTimestamp());
    accountRepository.save(itMe);
  }

  @Override
  public void checkOtp(long profileId, String otp) {
    AccountOtp accountOtp = accountOtpRepository.findByAccountId(profileId).orElse(null);
    if (accountOtp == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_OTP_NOT_EXISTS);
    }
    if (!accountOtp.getOtp().equals(otp)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_OTP_WRONG);
    }
    accountOtpRepository.delete(accountOtp);
  }

  @Override
  public PageResponse<AccountResponse> searchByPhoneNumber(String phoneNumber, PageRequest request) {
    Pageable pageable = PageUtils.toPageable(request);
    List<AccountResponse> accountResponses = new ArrayList<>();
    phoneNumber = "%" + phoneNumber + "%";
    Page<AccountPro> accounts = accountRepository.findByPhoneNumberLike(phoneNumber, pageable);
    for (AccountPro x : accounts) {
      AccountResponse accountResponse = new AccountResponse();
      accountResponse.setDisableAt(x.getAccount().getDisableAt());
      accountResponse.setCreateAt(x.getAccount().getCreateAt());
      accountResponse.setRole(x.getAccount().getRole());
      accountResponse.setPhoneNumber(x.getAccount().getPhoneNumber());
      accountResponse.setId(x.getAccount().getId());
      accountResponse.setStatus(x.getAccount().getStatus());
      accountResponse.setUsername(x.getAccount().getUsername());
      accountResponse.setName(x.getName());
      accountResponses.add(accountResponse);
    }
    PageResponse<AccountResponse> acc = new PageResponse<>(
        accountResponses,
        accounts.getTotalElements(),
        pageable.getPageSize()
    );
    return acc;
  }

  @Override
  public void updateEnable(Long id) {
    Optional<Account> accountOptional=accountRepository.findById(id);
    if (!accountOptional.isPresent()){
      throw new RuntimeException("account not found"+id);
    }
    Account account=accountOptional.get();
    account.setStatus(AccountStatus.DISABLE);
    account.setDisableAt(DateUtils.currentTimestamp());
    accountRepository.save(account);
  }
}