package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    @Query("SELECT r FROM Reaction r " +
            "JOIN ReactionPost rp ON rp.reaction = r " +
            "WHERE rp.post.id = ?1 ")
    Page<Reaction> findAllByReactionPostId(Long postId, Pageable pageable);
}
