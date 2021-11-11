package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.response.AccountProfileResponse;
import com.datn.topfood.dto.response.AccountPro;
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

    @Query("SELECT new com.datn.topfood.dto.response.AccountPro(" +
            "prof.name, " +
            "acc " +
            ") FROM Account acc JOIN Profile prof ON prof.account = acc " +
            "WHERE acc.phoneNumber like ?1")
    Page<AccountPro> findByPhoneNumberLike(String phoneNumber, Pageable request);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);
}
