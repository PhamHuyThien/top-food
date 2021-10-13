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
public class CreateConversationRequest extends MessageInfo {
    String name;
    Long accountIdAdd;
}
