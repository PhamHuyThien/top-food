package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.repository.custom.impl.AccountCustomRepository;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.AccountProfileResponse;
import com.datn.topfood.dto.response.AccountResponse;
import com.datn.topfood.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Account> findByPhoneNumberLike(String phoneNumber, Pageable request);
}
