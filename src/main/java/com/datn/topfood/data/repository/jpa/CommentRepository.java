package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c " +
            "JOIN CommentPost cp ON cp.comment = c " +
            "WHERE cp.post.id = ?1")
    Page<Comment> findAllByCommentPostId(Long postId, Pageable pageable);
}
