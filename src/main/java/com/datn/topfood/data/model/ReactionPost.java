package com.datn.topfood.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ReactionPost extends Base {

    @ManyToOne
    @JoinColumn(name = "reaction_id")
    Reaction reaction;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
