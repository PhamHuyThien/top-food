package com.datn.topfood.dto.messages;

import com.datn.topfood.dto.response.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageRoomTransferResponse extends MessageInfo {
    ProfileResponse profileCheckIn;
}
