package com.datn.topfood.data.model;

import java.util.List;

import javax.persistence.*;

import com.datn.topfood.util.enums.ParticipantsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Participants extends Base{

	@ManyToOne
	@JoinColumn(name = "conversation_id")
	private Conversation conversation;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@OneToOne
	private Messages messages;
}
