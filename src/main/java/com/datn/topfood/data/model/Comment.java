package com.datn.topfood.data.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comment extends Base{

	private String content;
	
	@ManyToOne 
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			  name = "comment_reaction", 
			  joinColumns = @JoinColumn(name = "comment_id"), 
			  inverseJoinColumns = @JoinColumn(name = "reaction_id"))
	private Set<Reaction> reactions;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			  name = "comment_file", 
			  joinColumns = @JoinColumn(name = "comment_id"), 
			  inverseJoinColumns = @JoinColumn(name = "file_id")) 
	private Set<File> files;
	
	@OneToMany(mappedBy = "comment",cascade = CascadeType.ALL)
	private List<CommentReply> commentReplys;
	
	@OneToMany(mappedBy = "comment",cascade = CascadeType.ALL)
	private List<CommentPost> commentPosts;
	
}
