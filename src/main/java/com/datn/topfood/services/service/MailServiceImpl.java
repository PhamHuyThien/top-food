package com.datn.topfood.services.service;

import com.datn.topfood.services.interf.MailService;
import com.datn.topfood.util.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendOtp(String otp, String emailReceiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailReceiver);
        message.setSubject(Message.MAIL_SEND_OTP_SUBJECT);
        message.setText(String.format(Message.MAIL_SEND_OTP_TEXT, otp));
        javaMailSender.send(message);
    }


}
