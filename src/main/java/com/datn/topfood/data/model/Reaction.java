package com.datn.topfood.data.model;

import java.util.Set;

import javax.persistence.*;

import com.datn.topfood.util.enums.ReactType;
import com.datn.topfood.util.enums.ReactionType;
import lombok.*;
import org.apache.catalina.User;
import org.apache.commons.lang3.builder.ToStringExclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Reaction extends Base {
    @Enumerated(EnumType.STRING)
    ReactType type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;
}
