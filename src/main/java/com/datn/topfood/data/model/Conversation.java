package com.datn.topfood.data.model;

import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Conversation extends Base {

    @Column(length = 100)
    private String title;
    @Column(length = 100)
    private String image;
    @ManyToOne
    @JoinColumn(name = "create_by")
    private Account createBy;
}
