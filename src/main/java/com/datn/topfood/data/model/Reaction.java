package com.datn.topfood.data.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Reaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String type;
	
	@ManyToMany(mappedBy = "reactions")
	private Set<Post> posts;
	
	@ManyToMany(mappedBy = "reactions")
	private Set<Comment> comments;
}
