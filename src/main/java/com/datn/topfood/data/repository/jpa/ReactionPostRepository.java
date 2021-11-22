package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Reaction;
import com.datn.topfood.data.model.ReactionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReactionPostRepository extends JpaRepository<ReactionPost, Long> {
    @Query("SELECT CASE WHEN COUNT(rp) > 0 THEN TRUE ELSE FALSE END FROM ReactionPost rp " +
            "WHERE rp.reaction.account.id = ?1 AND rp.post.id = ?2")
    boolean existsByReactionPost(Long accountId, Long postId);

    @Query("SELECT rp FROM ReactionPost rp " +
            "WHERE rp.reaction.account.id = ?1 AND rp.post.id = ?2")
    ReactionPost getByAccountIdAndPostId(Long accountId, Long postId);
    
    @Query("select count(rp) from ReactionPost as rp where rp.post.id = ?1")
    Long totalReactionPost(Long id);
    
    @Query("select rp from ReactionPost as rp where rp.post.id = ?1 and rp.reaction.account.id = ?2")
    ReactionPost isMyReactionPost(Long postId,Long accountId);
}
