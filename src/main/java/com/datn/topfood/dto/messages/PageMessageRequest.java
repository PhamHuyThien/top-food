package com.datn.topfood.dto.messages;

import com.datn.topfood.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageMessageRequest extends PageRequest {
    Long accountId;
    Long conversationId;
}
