package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Messages;
import com.datn.topfood.data.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessagesResponse {
    Long id;
    Date createAt;
    Date updateAt;
    String message;
    int heart;
    MessagesResponse quoteMessage;
    Profile createBy;
}
