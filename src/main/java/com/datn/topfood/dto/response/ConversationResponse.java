package com.datn.topfood.dto.response;

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
public class ConversationResponse {
    Long id;
    String title;
    Date createAt;
    Date updateAt;
    Profile createBy;
}
