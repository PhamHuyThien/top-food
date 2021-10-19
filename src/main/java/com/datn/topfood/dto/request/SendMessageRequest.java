package com.datn.topfood.dto.request;

import com.datn.topfood.dto.messages.MessageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendMessageRequest extends MessageInfo {
    String message;
    Long quoteMessageId;
    List<String> attachments;
}