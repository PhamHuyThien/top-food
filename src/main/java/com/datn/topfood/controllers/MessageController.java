package com.datn.topfood.controllers;

import com.datn.topfood.dto.messages.*;
import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.ConversationResponse;
import com.datn.topfood.dto.response.MessagesResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.MessageService;
import com.datn.topfood.util.constant.Message;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/{token}/create-conversation")
    @SendTo("/messages/inbox/{token}")
    public MessageResponse<ConversationResponse> createConversation(@Payload CreateConversationRequest createConversationRequest) {
        return messageService.createConversation(createConversationRequest);
    }

    @MessageMapping("/{token}/send")
    @SendTo("/messages/inbox/{token}")
    public MessageResponse<MessagesResponse> sendMessage(@Payload SendMessageRequest sendMessageRequest) {
        return messageService.sendMessage(sendMessageRequest);
    }

    @MessageMapping("/{token}/get-list-conversation")
    @SendTo("/messages/inbox/{token}")
    public PageMessageResponse<ConversationResponse> getListConversation(@Payload PageMessageRequest pageMessageRequest) {
        return messageService.getListConversation(pageMessageRequest);
    }

    @MessageMapping("/{token}/get-list-message")
    @SendTo("/messages/inbox/{token}")
    public PageMessageResponse<MessagesResponse> getListMessage(@Payload PageMessageRequest pageMessageRequest) {
        return messageService.getListMessages(pageMessageRequest);
    }

    @MessageMapping("/{token}/remove-message")
    @SendTo("/messages/inbox/{token}")
    public MessageResponse<MessageRemoveResponse> deleteMessage(@Payload MessageRemoveRequest messageRemoveRequest) {
        return messageService.deleteMessage(messageRemoveRequest);
    }

    @MessageMapping("/{token}/delete-conversation")
    @SendTo("/messages/inbox/{token}")
    public MessageResponse<MessageDeleteConversationResponse> deleteConversation(@Payload MessageDeleteConversationRequest messageDeleteConversationRequest) {
        return messageService.deleteConversation(messageDeleteConversationRequest);
    }

    @MessageMapping("/{token}/heart-message")
    @SendTo("/messages/inbox/{token}")
    public MessageResponse<MessageHeartResponse> reactHeart(@Payload MessageHeartRequest messageHeartRequest){
        return messageService.reactHeart(messageHeartRequest);
    }

    @MessageMapping("/{token}/info-conversation")
    @SendTo("/messages/inbox/{token}")
    public MessageResponse<MessageInfoResponse> infoConversation(@Payload MessageInfoRequest messageInfoRequest){
        return messageService.infoConversation(messageInfoRequest);
    }

    @MessageMapping("/{token}/add-member")
    @SendTo("/messages/inbox/{token}")
    public MessageResponse<MessageAddResponse> addMember(@Payload MessageAddRequest messageAddRequest){
        return messageService.addMember(messageAddRequest);
    }

    @MessageMapping("/{token}/delete-member")
    @SendTo("/messages/inbox/{token}")
    public  MessageResponse<MessageKickResponse> deleteMember(@Payload MessageKickRequest messageKickRequest){
        return messageService.deleteMember(messageKickRequest);
    }

    @MessageMapping("/{token}/update-conversation")
    @SendTo("/messages/inbox/{token}")
    public  MessageResponse<MessageUpdateConversationResponse> updateConversation(@Payload MessageUpdateConversationRequest messageUpdateConversationRequest){
        return messageService.updateConversation(messageUpdateConversationRequest);
    }

    @MessageMapping("/{token}/room-transfer")
    @SendTo("/messages/inbox/{token}")
    public  MessageResponse<MessageRoomTransferResponse> roomTransfer(@Payload MessageRoomTransferRequest messageRoomTransferRequest){
        return messageService.roomTransfer(messageRoomTransferRequest);
    }

}
