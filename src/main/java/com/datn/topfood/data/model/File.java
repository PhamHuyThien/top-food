package com.datn.topfood.data.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

import com.datn.topfood.util.enums.FileType;
@Entity
public class File extends Base{

	private String path;
	
	@Enumerated(EnumType.STRING)
	private FileType type;
	
	@ManyToMany(mappedBy = "files")
	private Set<Comment> comments;
	
	@ManyToMany(mappedBy = "files")
	private Set<Post> posts;
	
	@ManyToMany(mappedBy = "files")
	private Set<Food> foods;
}
