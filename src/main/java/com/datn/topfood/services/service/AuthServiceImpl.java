package com.datn.topfood.services.service;

import com.datn.topfood.authen.JwtTokenProvider;
import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.request.LoginRequest;
import com.datn.topfood.dto.response.LoginResponse;
import com.datn.topfood.services.interf.AuthService;
import com.datn.topfood.util.constant.Message;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public LoginResponse loginWithUsername(LoginRequest loginRequest) {
        //JPA sẽ tìm ra tk mk ở đây....
        String username = "thiendeptrai";
        String password = "p4ssw0rd";
        if (!(loginRequest.getUsername().equals(username) && loginRequest.getPassword().equals(password))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_USERNAME_OR_PASSWORD_WRONG);
        }
        Account account = new Account(username, password);
        String token = JwtTokenProvider.generateToken(account);
        return new LoginResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
