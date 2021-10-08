package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.SendMessageRequest;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.MessageService;
import com.datn.topfood.util.constant.Message;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Operation(description = "API gửi tin nhắn")
    @PostMapping("/send")
    public ResponseEntity<Response<Void>> sendMessage(SendMessageRequest sendMessageRequest) {
        messageService.sendMessage(sendMessageRequest);
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS));
    }
}
