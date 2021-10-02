package com.datn.topfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyInvitationFriendRequest {

	String usernameReplyPerson;
	String usernameRequestPerson;
	Boolean statusReply;
	
}
