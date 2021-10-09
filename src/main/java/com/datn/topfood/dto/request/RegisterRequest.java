package com.datn.topfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	String username;
	String password;
	String phoneNumber;
	String email;
	String name;
	Timestamp birthday;
}
