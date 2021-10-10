package com.datn.topfood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProfileResponse {
  Long id;
  String cover;
  String avatar;
  String bio;
  String address;
  String name;
  Date birthday;
  String phoneNumber;
  String email;
}
