package com.datn.topfood.data.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Comment extends Base{

	@Column(length = 1000)
	private String content;
	
	@ManyToOne 
	@JoinColumn(name = "account_id")
	private Account account;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			  name = "comment_reaction", 
			  joinColumns = @JoinColumn(name = "comment_id"), 
			  inverseJoinColumns = @JoinColumn(name = "reaction_id"))
	private Set<Reaction> reactions;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			  name = "comment_file", 
			  joinColumns = @JoinColumn(name = "comment_id"), 
			  inverseJoinColumns = @JoinColumn(name = "file_id")) 
	private Set<File> files;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@OneToMany(mappedBy = "comment",cascade = CascadeType.ALL)
	private List<CommentReply> commentReplys;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@OneToMany(mappedBy = "comment",cascade = CascadeType.ALL)
	private List<CommentPost> commentPosts;
	
}
