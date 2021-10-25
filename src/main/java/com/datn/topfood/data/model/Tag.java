package com.datn.topfood.data.model;


import javax.persistence.*;

import lombok.*;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Tag extends Base {
    Long id;
    String tagName;
    String image;
    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL)
    List<Food> food;
    boolean enable=false;
    @ManyToMany(mappedBy = "tag")
    Collection<Post> posts;
    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
