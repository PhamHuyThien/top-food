package com.datn.topfood.data.model;

import javax.persistence.*;

import com.datn.topfood.util.enums.ReactType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Reaction extends Base {
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    ReactType type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;
}
