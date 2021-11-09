package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.CommentPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPostRepository extends JpaRepository<CommentPost, Long> {
}
