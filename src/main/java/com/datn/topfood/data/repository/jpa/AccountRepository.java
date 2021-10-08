package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.response.AccountProfileResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByEmail(String email);
    Account findByPhoneNumber(String phone);
    @Query("SELECT new com.datn.topfood.dto.response.AccountProfileResponse(" +
            "acc, " +
            "prof " +
            ") FROM Account acc JOIN Profile prof ON prof.account = acc " +
            "WHERE acc.id = ?1")
    AccountProfileResponse getAccountProfileFrivate(Long accountId);
}
