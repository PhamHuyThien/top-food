package com.datn.topfood.data.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Tag extends Base{
	Long id;
	String tagName;
	@OneToMany(mappedBy = "tag",cascade = CascadeType.ALL)
	private List<SubTag> subTags;
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@OneToMany(mappedBy = "tag",cascade = CascadeType.ALL)
	private List<Favorite> favorites;
	
}
