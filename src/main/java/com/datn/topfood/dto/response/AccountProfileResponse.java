package com.datn.topfood.dto.response;

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
    Long id;
    String phoneNumber;
    String email;
    String address;
    String avatar;
    Timestamp birthday;
    String bio;
    String cover;
    String name;
}
