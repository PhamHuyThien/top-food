package com.datn.topfood.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class CommentReply extends Base{

	@Column(length = 1000)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment comment;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
}
