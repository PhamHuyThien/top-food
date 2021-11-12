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
public class CommentReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

    @ManyToOne
    @JoinColumn(name = "comment_reply_id")
    private Comment commentReply;

}
