package com.datn.topfood.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRemoveRequest extends MessageInfo {
    Long idMessageRemove;
}
