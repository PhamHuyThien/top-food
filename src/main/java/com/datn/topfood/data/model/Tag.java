package com.datn.topfood.data.model;


import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Tag extends Base {
    Long id;
    String tagName;
    String image;

    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
