package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.CommentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentPostRepository extends JpaRepository<CommentPost, Long> {
    @Query("SELECT cp FROM CommentPost cp " +
            "WHERE cp.comment.id = ?1 AND cp.comment.account.id = ?2")
    Optional<CommentPost> findByCommentIdAndAccountId(Long commentId, Long accountId);
}
