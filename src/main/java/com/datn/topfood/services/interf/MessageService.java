package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.dto.messages.MessageResponse;
import com.datn.topfood.dto.messages.MessageSend;
import com.datn.topfood.dto.messages.MessageTokenInfo;
import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.ConversationResponse;
import com.datn.topfood.dto.response.MessagesResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.SendMessageResponse;

public interface MessageService {
    Conversation createConversation(CreateConversationRequest createConversationRequest);

    SendMessageResponse sendMessage(SendMessageRequest sendMessageRequest);

    PageResponse<ConversationResponse> getListConversation(PageRequest pageRequest);

    PageResponse<MessagesResponse> getListMessages(Long conversationId, PageRequest pageRequest);

    void deleteMessage(Long messageId);

    void deleteConversation(Long conversationId);

    void reactHeart(Long messageId);

    void addMember(AddMemeberRequest addMemeberRequest);

    void deleteMember(DeleteMemeberRequest deleteMemeberRequest);

    MessageResponse<MessageTokenInfo> getInfoToken(String token);

    MessageResponse<MessagesResponse> sockJsSend(String token, MessageSend messageSend);
}
