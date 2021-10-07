package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.SendMessageRequest;

public interface MessageService {
    void sendMessage(SendMessageRequest sendMessageRequest);
}
