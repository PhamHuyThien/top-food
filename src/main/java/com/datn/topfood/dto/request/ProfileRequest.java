package com.datn.topfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
  String cover;
  String bio;
  String name;
  String address;
  Timestamp birthday;
  String avatar;
}
