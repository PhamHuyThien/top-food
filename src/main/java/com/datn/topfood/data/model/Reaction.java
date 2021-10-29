package com.datn.topfood.data.model;

import java.util.Set;

import javax.persistence.*;

import com.datn.topfood.util.enums.ReactionType;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Reaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ReactionType type;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(mappedBy = "reactions")
	private Set<Post> posts;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(mappedBy = "reactions")
	private Set<Comment> comments;
}
