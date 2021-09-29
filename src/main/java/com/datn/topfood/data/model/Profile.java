package com.datn.topfood.data.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cover;
	private String avatar;
	private String bio;
	private String address;
	private String name;
	private Integer age;
	private Timestamp updateAt;
	
	@OneToOne
	private Account account;
	
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL)
	private List<Post> posts;
	
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL)
	private List<Food> foods;
}
