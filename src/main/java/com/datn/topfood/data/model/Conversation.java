package com.datn.topfood.data.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
public class Conversation extends Base{

	private String title;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@OneToMany(mappedBy = "conversation",cascade = CascadeType.ALL)
	private List<Messages> messages;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@OneToMany(mappedBy = "conversation",cascade = CascadeType.ALL)
	private List<Participants> participants;
}
