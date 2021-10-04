package com.datn.topfood.services;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.AccountStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

public class BaseService {
    public Account itMe() {
        Object objectAccount = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (objectAccount == null || !(objectAccount instanceof Account)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.AUTH_LOGIN_USERNAME_WRONG);
        }
        return (Account) objectAccount;
    }

    public void checkMe() {
        Account account = itMe();
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_NOT_ACTIVE);
        }
    }
}
