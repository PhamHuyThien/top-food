package com.datn.topfood.services.interf;

import com.datn.topfood.dto.messages.*;
import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.ConversationResponse;
import com.datn.topfood.dto.response.MessagesResponse;

public interface MessageService {
    MessageResponse<ConversationResponse> createConversation(CreateConversationRequest createConversationRequest);

    MessageResponse<MessagesResponse> sendMessage(SendMessageRequest sendMessageRequest);

    PageMessageResponse<ConversationResponse> getListConversation(PageMessageRequest pageMessageRequest);

    PageMessageResponse<MessagesResponse> getListMessages(PageMessageRequest pageMessageRequest);

    MessageResponse<MessageRemoveResponse> deleteMessage(MessageRemoveRequest messageRemoveRequest);

    MessageResponse<MessageDeleteConversationResponse> deleteConversation(MessageDeleteConversationRequest messageDeleteConversationRequest);

    MessageResponse<MessageHeartResponse> reactHeart(MessageHeartRequest messageHeartRequest);

    MessageResponse<MessageAddResponse> addMember(MessageAddRequest messageAddRequest);

    MessageResponse<MessageKickResponse> deleteMember(MessageKickRequest messageKickRequest);

    MessageResponse<MessageInfoResponse> infoConversation(MessageInfoRequest messageInfoRequest);

    MessageResponse<MessageUpdateConversationResponse> updateConversation(MessageUpdateConversationRequest messageUpdateConversationRequest);
}
