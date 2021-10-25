package com.datn.topfood.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToMany(mappedBy = "report")
    Collection<Rule> rules;
    @OneToOne
    Account account;
    @OneToOne
    Post post;
    @OneToOne
    Comment comment;
}
