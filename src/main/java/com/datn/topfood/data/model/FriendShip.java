package com.datn.topfood.data.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FriendShip extends Base{

	private String status;
	
	@ManyToOne
	@JoinColumn(name = "accountrequest_id")
	private Account accountRequest;
	
	@ManyToOne
	@JoinColumn(name = "accountaddress_id")
	private Account accountAddressee;
}
