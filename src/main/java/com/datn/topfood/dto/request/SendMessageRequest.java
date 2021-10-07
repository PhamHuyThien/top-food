package com.datn.topfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendMessageRequest {
    Long idConversation;
    String message;
    Long quoteMessageId;
}