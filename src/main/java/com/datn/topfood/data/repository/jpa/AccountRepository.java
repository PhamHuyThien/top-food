package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.response.AccountProfileResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByEmail(String email);
    Account findByPhoneNumber(String phone);
    @Query("SELECT new com.datn.topfood.dto.response.AccountProfileResponse( " +
            "acc.id, " +
            "acc.phoneNumber, " +
            "acc.email, " +
            "prof.address, " +
            "prof.avatar, " +
            "prof.birthday, " +
            "prof.bio, " +
            "prof.cover, " +
            "prof.name " +
            ") FROM Account acc JOIN Profile prof ON prof.account = acc")
    AccountProfileResponse getAccountProfileFrivate(Long accountId);
}
