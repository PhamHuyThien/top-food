package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.dto.messages.*;
import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.ConversationResponse;
import com.datn.topfood.dto.response.MessagesResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.SendMessageResponse;

public interface MessageService {
    MessageResponse<ConversationResponse> createConversation(CreateConversationRequest createConversationRequest);

    MessageResponse<MessagesResponse> sendMessage(SendMessageRequest sendMessageRequest);

    PageMessageResponse<ConversationResponse> getListConversation(PageMessageRequest pageMessageRequest);

    PageMessageResponse<MessagesResponse> getListMessages(PageMessageRequest pageMessageRequest);

    MessageResponse<MessageRemoveResponse> deleteMessage(MessageRemoveRequest messageRemoveRequest);

    MessageResponse<MessageDeleteConversationResponse> deleteConversation(MessageDeleteConversationRequest messageDeleteConversationRequest);

    MessageResponse<MessageHeartResponse> reactHeart(MessageHeartRequest messageHeartRequest);

    void addMember(AddMemeberRequest addMemeberRequest);

    void deleteMember(DeleteMemeberRequest deleteMemeberRequest);
}
