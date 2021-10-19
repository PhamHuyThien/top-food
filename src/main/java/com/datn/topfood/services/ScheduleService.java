package com.datn.topfood.services;

import com.datn.topfood.data.model.AccountOtp;
import com.datn.topfood.data.repository.jpa.AccountOtpRepository;
import com.datn.topfood.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class ScheduleService {
    @Autowired
    AccountOtpRepository accountOtpRepository;

    @Value("${topfood.otp.expired}")
    private long timeExpired;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void clearOtpExpired() {
        Timestamp timestampExpired = DateUtils.longTimeToTimestamp(DateUtils.currentLongTime() - timeExpired);
        List<AccountOtp> accountOtpList = accountOtpRepository.findByCreateAtBefore(timestampExpired);
        for (AccountOtp accountOtp : accountOtpList) {
            accountOtpRepository.delete(accountOtp);
            log.info("Delete " + accountOtp + " expired success.");
        }
    }
}
