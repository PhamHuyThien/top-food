package com.datn.topfood.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Favorite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer count;
	
	@ManyToOne
	@JoinColumn(name = "tag_id")
	private Tag tag;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
}
