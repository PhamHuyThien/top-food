package com.datn.topfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser {
    String username;
    String password;
    String phoneNumber;
    String email;
    String name;
    Timestamp birthday;
    String role;
    Integer city;
}
