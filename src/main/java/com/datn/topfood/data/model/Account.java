package com.datn.topfood.data.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account extends Base implements UserDetails {
	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String phoneNumber;
	@Column(unique = true)
	private String email;
	private AccountStatus status;
	private AccountRole role;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "accountRequest", cascade = CascadeType.ALL)
	private List<FriendShip> friendShipsRequest;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "accountAddressee", cascade = CascadeType.ALL)
	private List<FriendShip> friendShipsAddressee;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Approach> approachs;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Intereact> intereacts;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Messages> messages;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Participants> participants;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Favorite> favorites;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<CommentReply> commentReplys;
	
	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@JsonIgnore
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<AccountFollow> accountFollow;

	public Account(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
