package com.datn.topfood.data.model;

import java.util.Set;

import javax.persistence.*;

import lombok.*;

import com.datn.topfood.util.enums.FileType;
import org.apache.commons.lang3.builder.ToStringExclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class File extends Base{

	@Column(length = 100)
	private String path;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
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
