package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.request.SendMessageRequest;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends BaseService implements MessageService {
    @Override
    public void sendMessage(SendMessageRequest sendMessageRequest) {
        Account itMe = itMe();
    }
}
