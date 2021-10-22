package com.datn.topfood.dto.response;

import com.datn.topfood.util.enums.FriendShipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchProfileResponse extends ProfileResponse {
    FriendShipStatus friendStatus;
    Boolean isPersonSending;
}
