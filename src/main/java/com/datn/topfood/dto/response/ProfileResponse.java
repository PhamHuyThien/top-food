package com.datn.topfood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
  Integer age;
  String phone;
  String email;
}
