package com.datn.topfood.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubTag extends Base{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String subTagName;
  @ManyToOne
  @JoinColumn(name = "tag_id")
  Tag tag;
}
