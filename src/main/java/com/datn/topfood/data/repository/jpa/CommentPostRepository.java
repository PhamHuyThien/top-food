package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.CommentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentPostRepository extends JpaRepository<CommentPost, Long> {
    @Query("SELECT cp FROM CommentPost cp " +
            "WHERE cp.post.id = ?1 AND cp.comment.id = ?2 AND cp.comment.account.id = ?3")
    Optional<CommentPost> findByPostIdAndCommentIdAndAccountId(Long postId, Long commentId, Long accountId);
}
