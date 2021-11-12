package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.File;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT f FROM File f " +
            "JOIN CommentFile cf ON cf.file = f " +
            "WHERE cf.comment.id = ?1")
    List<File> findAllByCommentFileId(Long commentId);
}
