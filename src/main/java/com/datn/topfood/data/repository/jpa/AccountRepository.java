package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByEmail(String email);
    Account findByPhoneNumber(String phone);
}
