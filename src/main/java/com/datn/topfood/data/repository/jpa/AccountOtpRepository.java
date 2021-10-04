package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.AccountOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountOtpRepository extends JpaRepository<AccountOtp, Long> {
    Optional<AccountOtp> findByAccountId(long accountId);
}
