package com.datn.topfood.data.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Messages extends Base{

	private String message;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "messages_id")
	private Messages messages;
	
	@ManyToOne
	@JoinColumn(name = "conversation_id")
	private Conversation conversation;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@OneToMany(mappedBy = "messages",cascade = CascadeType.ALL)
	private List<Attachments> attachments;
}
