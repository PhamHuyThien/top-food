package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.dto.request.AddMemeberRequest;
import com.datn.topfood.dto.request.CreateConversationRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.SendMessageRequest;
import com.datn.topfood.dto.response.ConversationResponse;
import com.datn.topfood.dto.response.MessagesResponse;
import com.datn.topfood.dto.response.PageResponse;

public interface MessageService {
    Conversation createConversation(CreateConversationRequest createConversationRequest);

    void sendMessage(SendMessageRequest sendMessageRequest);

    PageResponse<ConversationResponse> getListConversation(PageRequest pageRequest);

    PageResponse<MessagesResponse> getListMessages(Long conversationId, PageRequest pageRequest);

    void deleteMessage(Long messageId);

    void deleteConversation(Long conversationId);

    void reactHeart(Long messageId);

    void addMember(AddMemeberRequest addMemeberRequest);
}
