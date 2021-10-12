package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountPro {
    String name;
    Account account;
}
