package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.LoginRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.LoginResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    LoginResponse loginWithUsername(LoginRequest loginRequest);
    Boolean insertAccount(RegisterRequest registerRequest);
}
