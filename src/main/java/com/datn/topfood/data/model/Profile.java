package com.datn.topfood.data.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String cover;

    @Column(length = 100)
    private String avatar;

    @Column(length = 200)
    private String bio;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String name;
    
    @Column
    private Integer city;
    
    @Column(length = 200)
    private String foodHot;
    
    private Timestamp birthday;

    private Timestamp updateAt;

    @OneToOne
    @JsonIgnore
    private Account account;

    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts;

    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Food> foods;

	public Profile(Long id, String cover, String avatar, String bio, String address, String name, Timestamp birthday,
			Timestamp updateAt, Account account, List<Post> posts, List<Food> foods) {
		super();
		this.id = id;
		this.cover = cover;
		this.avatar = avatar;
		this.bio = bio;
		this.address = address;
		this.name = name;
		this.birthday = birthday;
		this.updateAt = updateAt;
		this.account = account;
		this.posts = posts;
		this.foods = foods;
	}
	
	public Profile(Integer city,Long id, String cover, String avatar, String bio, String address, String name, Timestamp birthday,
			Timestamp updateAt, Account account, List<Post> posts, List<Food> foods) {
		super();
		this.city = city;
		this.id = id;
		this.cover = cover;
		this.avatar = avatar;
		this.bio = bio;
		this.address = address;
		this.name = name;
		this.birthday = birthday;
		this.updateAt = updateAt;
		this.account = account;
		this.posts = posts;
		this.foods = foods;
	}
}
