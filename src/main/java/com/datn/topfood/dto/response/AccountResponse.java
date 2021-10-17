package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
  Long id;
  String username;
  String phoneNumber;
  AccountStatus status;
  String email;
  String name;
  AccountRole role;
  Timestamp createAt;
  Timestamp disableAt;
}
