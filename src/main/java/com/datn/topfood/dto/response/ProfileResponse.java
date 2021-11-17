package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Profile;

import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    Long accountId;
    String username;
    String phoneNumber;
    String email;
    AccountRole role;
    Profile profile;
}
