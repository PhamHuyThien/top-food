package com.datn.topfood.data.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Attachments extends Base{

	private String fileUrl;
	
	@ManyToOne
	@JoinColumn(name = "messages_id")
	private Messages messages;
}
