package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    @Query("SELECT CASE WHEN COUNT(cr) > 0 THEN TRUE ELSE FALSE END FROM CommentReaction cr " +
            "WHERE cr.reaction.account.id = ?1 AND cr.comment.id = ?2")
    boolean existsByCommentReaction(Long accountId, Long commentId);

    @Query("SELECT cr FROM CommentReaction cr " +
            "WHERE cr.reaction.account.id = ?1 AND cr.comment.id = ?2")
    CommentReaction getByAccountIdAndCommentId(Long accountId, Long commentId);
}
