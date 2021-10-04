package com.datn.topfood.services.service;

import com.datn.topfood.authen.JwtTokenProvider;
import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.AccountOtp;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.jpa.AccountOtpRepository;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.ForgotPasswordRequest;
import com.datn.topfood.dto.request.LoginRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.LoginResponse;
import com.datn.topfood.services.interf.AuthService;
import com.datn.topfood.services.interf.MailService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.constant.Message;

import java.sql.Timestamp;
import java.util.Random;

import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    MailService mailService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    AccountOtpRepository accountOtpRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value("${toopfood.otp.expired}")
    private long otpTimeExpired;

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
        if (account.getStatus() == AccountStatus.DISABLE) {
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
        account.setStatus(AccountStatus.WAIT_ACTIVE);
        account.setRole(AccountRole.ROLE_USER);
        accountRepository.save(account);
        Profile profile = new Profile();
        profile.setAccount(account);
        profileRepository.save(profile);
        return true;
    }

    @Override
    @Transactional
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Account account = accountRepository.findByEmail(forgotPasswordRequest.getEmail());
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_EMAIL_NOT_EXISTS);
        }
        AccountOtp accountOtp = accountOtpRepository.findByAccountId(account.getId()).orElse(null);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_OTP_NOT_EXISTS);
        }
        if (!accountOtp.getOtp().equals(forgotPasswordRequest.getOtp())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_OTP_WRONG);
        }
        accountOtpRepository.delete(accountOtp);
        String newPassword = generateRandomPassword(10);
        account.setPassword(passwordEncoder.encode(newPassword));
        account.setUpdateAt(DateUtils.currentTimestamp());
        accountRepository.save(account);
        return newPassword;
    }


    private String generateRandomPassword(int len) {
        final String BASE_STRING = "QWERTYUIOPASDFGHJKLZXCVBNM[];',./-=!@#$%^&*()_+";
        final char[] BASE_CHAR = BASE_STRING.toCharArray();
        StringBuilder passwordRand = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            passwordRand.append(BASE_CHAR[random.nextInt(BASE_CHAR.length)]);
        }
        return passwordRand.toString();
    }

    @Override
    @Transactional
    public void getOtp(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_EMAIL_NOT_EXISTS);
        }
        if (account.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_OTP_EMAIL_IS_NULL);
        }
        final Timestamp currentTime = DateUtils.currentTimestamp();
        AccountOtp accountOtp = accountOtpRepository.findByAccountId(account.getId()).orElse(null);
        if (accountOtp != null) {
            Timestamp timestampExpired = new Timestamp(accountOtp.getCreateAt().getTime() + otpTimeExpired);
            if (DateUtils.currentDate().before(DateUtils.timestampToDate(timestampExpired))) {
                int second = DateUtils.secondsBetweenTwoDates(currentTime, timestampExpired);
                String message = String.format(Message.AUTH_OTP_IS_EXISTS, second);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }
            accountOtpRepository.delete(accountOtp);
        }
        final String otp = createOtp();
        mailService.sendOtp(otp, account.getEmail());
        accountOtp = new AccountOtp(null, otp, currentTime, account);
        accountOtpRepository.save(accountOtp);
    }

    public String createOtp() {
        StringBuilder otp = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            otp.append(rand.nextInt(10));
        }
        return otp.toString();
    }
}
