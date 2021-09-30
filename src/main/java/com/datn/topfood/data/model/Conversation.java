package com.datn.topfood.data.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Conversation extends Base{

	private String title;
	
	@OneToMany(mappedBy = "conversation",cascade = CascadeType.ALL)
	private List<Messages> messages;
	
	@OneToMany(mappedBy = "conversation",cascade = CascadeType.ALL)
	private List<Participants> participants;
}
