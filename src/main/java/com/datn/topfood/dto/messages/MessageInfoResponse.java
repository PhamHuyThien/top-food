package com.datn.topfood.dto.messages;

import com.datn.topfood.dto.response.ConversationResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageInfoResponse extends MessageInfo {
    ConversationResponse conversation;
    List<ProfileResponse> members;
}
