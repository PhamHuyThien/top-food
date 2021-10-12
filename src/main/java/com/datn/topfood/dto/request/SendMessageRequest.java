package com.datn.topfood.dto.request;

import com.datn.topfood.dto.messages.MessageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendMessageRequest extends MessageInfo {
    String message;
    Long quoteMessageId;
}