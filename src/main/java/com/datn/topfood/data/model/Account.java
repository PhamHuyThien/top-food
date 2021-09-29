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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Account extends Base implements UserDetails {
	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String phoneNumber;
	@Column(unique = true)
	private String email;
	private String status;
	
	@OneToOne
	private AccountOtp accountOtp;
	
	@OneToOne
	private Profile profile;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			  name = "account_role", 
			  joinColumns = @JoinColumn(name = "account_id"), 
			  inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "accountRequest", cascade = CascadeType.ALL)
	private List<FriendShip> friendShipsRequest;
	
	@OneToMany(mappedBy = "accountAddressee", cascade = CascadeType.ALL)
	private List<FriendShip> friendShipsAddressee;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Approach> approachs;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Intereact> intereacts;
	
	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
	private List<Messages> messages;
	
	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
	private List<Participants> participants;
	
	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
	private List<Favorite> favorites;
	
	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
	private List<CommentReply> commentReplys;
	
	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
	private List<AccountFollow> accountFollow;

	
	public Account(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
