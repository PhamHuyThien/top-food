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

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public LoginResponse loginWithUsername(LoginRequest loginRequest) {
        UserDetails userDetails = loadUserByUsername(loginRequest.getUsername());
        if(userDetails == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_LOGIN_USERNAME_WRONG);
        }
        if(!userDetails.getPassword().equals(loginRequest.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_LOGIN_PASSWORD_FAIL);
        }
        String token = JwtTokenProvider.generateToken(userDetails);
        return new LoginResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(s);
        return account;
    }

	@Override
	public void insertAccount(RegisterRequest registerRequest) {
		// TODO Auto-generated method stub
		if(accountRepository.findByUsername(registerRequest.getUsername())!=null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_USERNAME_ALREADY_EXIST);
		}
		if(accountRepository.findByEmail(registerRequest.getEmail())!=null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_EMAIL_ALREADY_EXIST);
		}
		if(accountRepository.findByPhoneNumber(registerRequest.getPhoneNumber())!=null){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_PHONENUMBER_ALREADY_EXIST);
		}
		
		Account account = modelMapper.map(registerRequest, Account.class);
		account.setCreateAt(DateUtils.currentTimestamp());
		Profile profile = profileRepository.save(modelMapper.map(registerRequest, Profile.class));
		account.setProfile(profile);
		
		accountRepository.save(account);
		
	}
}
