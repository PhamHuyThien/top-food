package com.datn.topfood.services;

import com.datn.topfood.data.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseService {
    public Account itMe() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
