package com.datn.topfood.data.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Tag extends Base {
    Long id;
    String tagName;
    String image;
    boolean disable;
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<Food> foods;
    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<Favorite> favorites;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    Collection<Post> posts;
}
