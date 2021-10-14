package com.datn.topfood.controllers;

import com.datn.topfood.data.model.Conversation;
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

    @Operation(description = "Chat nội bộ, khu vực để dev test")
    @GetMapping("/")
    public String getChatTemplate(){
        return "chat.html";
    }

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

    @Operation(description = "API Thêm thành viên vào nhóm")
    @PostMapping("/add-member")
    public ResponseEntity<Response<?>> addMember(@RequestBody AddMemeberRequest addMemeberRequest){
        messageService.addMember(addMemeberRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API kích thành viên khỏi nhóm")
    @DeleteMapping("/delete-member")
    public  ResponseEntity<Response<?>> deleteMember(@RequestBody DeleteMemeberRequest deleteMemeberRequest){
        messageService.deleteMember(deleteMemeberRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }
}
