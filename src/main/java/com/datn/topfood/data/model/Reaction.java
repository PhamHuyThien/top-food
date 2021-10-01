package com.datn.topfood.data.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String type;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(mappedBy = "reactions")
	private Set<Post> posts;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(mappedBy = "reactions")
	private Set<Comment> comments;
}
