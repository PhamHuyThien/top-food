package com.datn.topfood.data.model;

import javax.persistence.*;

import com.datn.topfood.util.enums.FollowStatus;
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
public class AccountFollow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private FollowStatus status;
	/*@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "profile_id")
	private Profile profile;*/
	@ManyToOne
	@JoinColumn(name = "accountrequest_folow")
	private Account accountRequest;

	@ManyToOne
	@JoinColumn(name = "accountaddress_folow")
	private Account accountAddressee;
	
}
