package com.datn.topfood.data.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@OneToMany(mappedBy = "messages",cascade = CascadeType.ALL)
	private List<Attachments> attachments;
}
