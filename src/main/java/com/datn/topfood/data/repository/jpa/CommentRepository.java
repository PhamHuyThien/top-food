package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c " +
            "JOIN CommentPost cp ON cp.comment = c " +
            "WHERE cp.post.id = ?1 AND c.disableAt IS NULL")
    Page<Comment> findAllByCommentPostId(Long postId, Pageable pageable);

    @Query("SELECT c FROM Comment c " +
            "JOIN CommentReply cr ON cr.commentReply = c " +
            "WHERE cr.comment.id = ?1 AND cr.commentReply.disableAt IS NULL")
    Page<Comment> findAllByCommentId(Long commentId, Pageable pageable);

    @Query("SELECT c FROM Comment c " +
            "WHERE  c.id = ?1 AND c.account.id = ?2")
    Optional<Comment> findByCommentIdAndAccountId(Long commentId, Long accountId);
}
