package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConversationResponse {
    String title;
    Date createAt;
    Profile createBy;
}
