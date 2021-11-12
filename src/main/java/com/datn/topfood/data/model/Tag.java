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
    @Column(length = 50)
    String tagName;
    @Column(length = 100)
    String image;
    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL)
    List<Food> food;
    boolean enable=false;
    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
