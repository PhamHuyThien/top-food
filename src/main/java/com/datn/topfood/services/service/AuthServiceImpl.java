package com.datn.topfood.services.service;

import com.datn.topfood.authen.JwtTokenProvider;
import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.LoginRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.LoginResponse;
import com.datn.topfood.services.interf.AuthService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.constant.Message;

import java.sql.Timestamp;
import java.util.Date;

import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse loginWithUsername(LoginRequest loginRequest) {
        UserDetails userDetails = loadUserByUsername(loginRequest.getUsername());
        Account account = (Account) userDetails;
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_LOGIN_USERNAME_WRONG);
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_LOGIN_PASSWORD_FAIL);
        }
        if(account.getStatus() == AccountStatus.DEACTIVE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_ACCOUNT_IS_DISABLE);
        }
        account.setPassword(null);
        Profile profile = profileRepository.findByAccountId(account.getId());
        String token = JwtTokenProvider.generateToken(userDetails);
        return new LoginResponse(token, account, profile);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(s);
        return account;
    }

    @Override
    @Transactional
    public Boolean insertAccount(RegisterRequest registerRequest) {
        if (accountRepository.findByUsername(registerRequest.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_USERNAME_ALREADY_EXIST);
        }
        if (accountRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_EMAIL_ALREADY_EXIST);
        }
        if (accountRepository.findByPhoneNumber(registerRequest.getPhoneNumber()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_PHONENUMBER_ALREADY_EXIST);
        }
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setPhoneNumber(registerRequest.getPhoneNumber());
        account.setEmail(registerRequest.getEmail());
        account.setCreateAt(DateUtils.currentTimestamp());
        account.setStatus(AccountStatus.ACTIVE);
        account.setRole(AccountRole.ROLE_USER);
        accountRepository.save(account);
        Profile profile = new Profile();
        profile.setAccount(account);
        profileRepository.save(profile);
        return true;
    }
}
