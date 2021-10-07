package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.dto.request.CreateConversationRequest;
import com.datn.topfood.dto.request.SendMessageRequest;

public interface MessageService {
    Conversation createConversation(CreateConversationRequest createConversationRequest);
    void sendMessage(SendMessageRequest sendMessageRequest);
}
