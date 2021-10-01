package com.datn.topfood.data.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.datn.topfood.util.enums.FileType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class File extends Base{

	private String path;
	
	@Enumerated(EnumType.STRING)
	private FileType type;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(mappedBy = "files")
	private Set<Comment> comments;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(mappedBy = "files")
	private Set<Post> posts;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(mappedBy = "files")
	private Set<Food> foods;
}
