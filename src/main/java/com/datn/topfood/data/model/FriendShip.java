package com.datn.topfood.data.model;

import javax.persistence.*;

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
	@Column(length = 20)
	private FriendShipStatus status;

	@ManyToOne
	@JoinColumn(name = "blockby_id")
	private Account blockBy;

	@ManyToOne
	@JoinColumn(name = "accountrequest_id")
	private Account accountRequest;
	
	@ManyToOne
	@JoinColumn(name = "accountaddress_id")
	private Account accountAddressee;
}
