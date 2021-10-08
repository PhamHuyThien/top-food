package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    Long accountId;
    String phoneNumber;
    String email;
    Profile profile;

}
