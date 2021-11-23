package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.AccountOtp;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.custom.impl.FriendshipCustomRepository;
import com.datn.topfood.data.repository.jpa.AccountOtpRepository;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FriendShipRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.AccountDetailResponse;
import com.datn.topfood.dto.response.AccountResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.AccountPro;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.AccountService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.AccountRole;
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
      accountResponse.setEmail(x.getAccount().getEmail());
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

  @Override
  public void createAccount(CreateUser request) {
    boolean mail=accountRepository.existsByEmail(request.getEmail());
    boolean username=accountRepository.existsByUsername(request.getUsername());
    boolean phoneNumber=accountRepository.existsByPhoneNumber(request.getPhoneNumber());
    if (mail && username && phoneNumber){
      throw new RuntimeException("username mail phoneNumber already exists");
    }
    Account account = new Account();
    account.setUsername(request.getUsername());
    account.setPassword(passwordEncoder.encode(request.getPassword()));
    account.setPhoneNumber(request.getPhoneNumber());
    account.setEmail(request.getEmail());
    AccountRole accountRole=AccountRole.valueOf(request.getRole());
    account.setRole(accountRole);
    account.setCreateAt(DateUtils.currentTimestamp());
    account.setStatus(AccountStatus.ACTIVE);
    accountRepository.save(account);
    Profile profile = new Profile();
    profile.setName(request.getName());
    profile.setBirthday(request.getBirthday());
    profile.setAccount(account);
    profile.setCity(request.getCity());
    profileRepository.save(profile);
  }

  @Override
  public void updateRole(UpdateRoleRequest request,Long id) {
    Account account=accountRepository.findById(id).orElseThrow(() -> new RuntimeException("account not found"));
    account.setUpdateAt(DateUtils.currentTimestamp());
    AccountRole accountRole=AccountRole.valueOf(request.getRole());
    account.setRole(accountRole);
    accountRepository.save(account);
  }

  @Override
  public void updateActive(Long id) {
    Account account=accountRepository.findById(id).orElseThrow(() -> new RuntimeException("account not found"));
    account.setUpdateAt(DateUtils.currentTimestamp());
    account.setStatus(AccountStatus.ACTIVE);
    accountRepository.save(account);
  }

  @Override
  public void deleteAccount(Long id) {
    Account account=accountRepository.findById(id).orElseThrow(() -> new RuntimeException("account not found"));
    profileRepository.deleteById(account.getId());
    accountRepository.deleteById(account.getId());
  }

  @Override
  public void resetPassword(Long id) {
    Account account=accountRepository.findById(id).orElseThrow(() -> new RuntimeException("account not found"));
    String password=passwordEncoder.encode("123");
    account.setPassword(password);
    accountRepository.save(account);
  }

  @Override
  public AccountDetailResponse getAccountById(Long id) {
    Profile profile=profileRepository.findById(id).orElseThrow(() -> new RuntimeException("account not found"));
    AccountDetailResponse accountDetailResponse = new AccountDetailResponse();
    accountDetailResponse.setDisableAt(profile.getAccount().getDisableAt());
    accountDetailResponse.setCreateAt(profile.getAccount().getCreateAt());
    accountDetailResponse.setRole(profile.getAccount().getRole());
    accountDetailResponse.setPhoneNumber(profile.getAccount().getPhoneNumber());
    accountDetailResponse.setId( profile.getAccount().getId());
    accountDetailResponse.setEmail(profile.getAccount().getEmail());
    accountDetailResponse.setStatus(profile.getAccount().getStatus());
    accountDetailResponse.setUsername(profile.getAccount().getUsername());
    accountDetailResponse.setName(profile.getName());
    accountDetailResponse.setAddress(profile.getAddress());
    accountDetailResponse.setImg(profile.getAvatar());
    return accountDetailResponse;
  }
}