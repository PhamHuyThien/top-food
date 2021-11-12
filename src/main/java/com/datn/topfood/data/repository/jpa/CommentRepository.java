package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
