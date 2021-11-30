package com.datn.topfood.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Report extends Base {
    @OneToOne
    Profile profile;
    @ManyToOne
    @JoinColumn(name = "report_id")
    Post post;
    @ManyToMany(mappedBy = "reports", fetch = FetchType.EAGER)
    List<Rule> rules;
}
