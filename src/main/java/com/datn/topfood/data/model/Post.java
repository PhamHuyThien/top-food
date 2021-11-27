package com.datn.topfood.data.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Post extends Base {

    @Column(length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
    
    public Post(String content, Profile profile, Set<File> files, List<Intereact> intereacts,
			List<CommentPost> commentPosts, List<Approach> approachs, Collection<Tag> tags) {
		super();
		this.content = content;
		this.profile = profile;
		this.files = files;
		this.intereacts = intereacts;
		this.commentPosts = commentPosts;
		this.approachs = approachs;
		this.tags = tags;
	}

	@EqualsAndHashCode.Exclude
    @ToStringExclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_file",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<File> files;

    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Intereact> intereacts;

    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentPost> commentPosts;

    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Approach> approachs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tag_post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Collection<Tag> tags;
    
    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_food",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private List<Food> foods;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Report> reports;
}
