package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountProfileResponse {
    Account account;
    Profile profile;
}
