package com.datn.topfood.data.model;

import java.util.Set;

import javax.persistence.*;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Food extends Base {

    @Column(length = 50)
    private String name;
    private Double price;
    @Column(length = 200)
    private String content;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    Tag tag;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @EqualsAndHashCode.Exclude
    @ToStringExclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "food_file",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    public Set<File> files;
}
