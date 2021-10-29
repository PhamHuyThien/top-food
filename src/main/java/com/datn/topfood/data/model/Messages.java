package com.datn.topfood.data.model;


import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Messages extends Base {
	@Column(length = 1000)
	private String message;
	private int heart;

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
}
