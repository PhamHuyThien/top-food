package com.datn.topfood.controllers;

import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.dto.request.CreateConversationRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.SendMessageRequest;
import com.datn.topfood.dto.response.ConversationResponse;
import com.datn.topfood.dto.response.MessagesResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.MessageService;
import com.datn.topfood.util.constant.Message;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Operation(description = "API tạo cuộc trò chuyện")
    @PostMapping("/create")
    public ResponseEntity<Response<Conversation>> createConversation(@RequestBody CreateConversationRequest createConversationRequest) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, messageService.createConversation(createConversationRequest)));
    }

    @Operation(description = "API gửi tin nhắn")
    @PostMapping("/send")
    public ResponseEntity<Response<Void>> sendMessage(@RequestBody SendMessageRequest sendMessageRequest) {
        messageService.sendMessage(sendMessageRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API Lấy danh sách cuộc trò chuyện")
    @GetMapping("/list-conversation")
    public ResponseEntity<PageResponse<ConversationResponse>> getListConversation(PageRequest pageRequest) {
        return ResponseEntity.ok(messageService.getListConversation(pageRequest));
    }

    @Operation(description = "API lấy danh sách tin nhắn cuộc trò chuyện")
    @GetMapping("/list-messages")
    public ResponseEntity<PageResponse<MessagesResponse>> getListMessage(@RequestParam Long conversationId, PageRequest pageRequest) {
        return ResponseEntity.ok(messageService.getListMessages(conversationId, pageRequest));
    }

    @Operation(description = "API gỡ tin nhắn")
    @DeleteMapping("/delete-message")
    public ResponseEntity<Response<?>> deleteMessage(@RequestParam Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }

    @Operation(description = "API xóa cuộc trò chuyện")
    @DeleteMapping("/delete-conversation")
    public ResponseEntity<Response<?>> deleteConversation(@RequestParam Long conversationId) {
        messageService.deleteConversation(conversationId);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }
}
