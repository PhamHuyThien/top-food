package com.datn.topfood.data.model;

import java.sql.Timestamp;

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
public class CommentPost extends Base{

	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment comment;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
}
