package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
}
