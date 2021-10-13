package com.datn.topfood.dto.messages;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Conversation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageTokenInfo {
    Conversation conversation;
    Account account;
}
