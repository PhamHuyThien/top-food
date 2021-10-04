package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.AccountOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface AccountOtpRepository extends JpaRepository<AccountOtp, Long> {
    Optional<AccountOtp> findByAccountId(long accountId);

    List<AccountOtp> findByCreateAtBefore(Timestamp timestamp);
}
