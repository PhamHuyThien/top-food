package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {
}
