package com.datn.topfood.data.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.datn.topfood.util.enums.FriendShipStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class FriendShip extends Base{

	@Enumerated(EnumType.STRING)
	private FriendShipStatus status;

	@ManyToOne
	@JoinColumn(name = "block_by")
	private Account blockBy;

	@ManyToOne
	@JoinColumn(name = "accountrequest_id")
	private Account accountRequest;
	
	@ManyToOne
	@JoinColumn(name = "accountaddress_id")
	private Account accountAddressee;
}
