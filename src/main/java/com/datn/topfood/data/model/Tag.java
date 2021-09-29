package com.datn.topfood.data.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tag extends Base{

	private String code;
	private String name;
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "tag_id")
	private Tag tag;
	
	@OneToMany(mappedBy = "tag",cascade = CascadeType.ALL)
	private List<Favorite> favorites;
	
}
