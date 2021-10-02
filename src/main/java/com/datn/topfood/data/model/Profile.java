package com.datn.topfood.data.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
	@JsonIgnore
	private Account account;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Post> posts;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Food> foods;
}
