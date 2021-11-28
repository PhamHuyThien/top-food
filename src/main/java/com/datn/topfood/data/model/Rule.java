package com.datn.topfood.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Rule extends Base {

  @Column(length = 100)
  String title;

  @Column(length = 200)
  String description;
  @ManyToMany( fetch = FetchType.EAGER)
  @JoinTable(
          name = "report_rule",
          joinColumns = @JoinColumn(name = "rule_id"),
          inverseJoinColumns = @JoinColumn(name = "report_id"))
  Collection<Report> reports;
}
