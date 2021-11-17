package com.datn.topfood.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.Reaction;
import com.datn.topfood.data.model.ReactionFood;

public interface ReactionFoodRepo extends JpaRepository<ReactionFood, Long>{

	@Query("select rf.reaction from ReactionFood as rf where rf.food.id = ?1 and rf.reaction.account.id = ?2")
	Reaction findReactionByFoodIdAndAccountId(Long foodId,Long accountId);
	
	@Query("select count(rf) from ReactionFood as rf where rf.food.id = ?1 and rf.reaction.disableAt is null")
	Long totalFoodReaction(Long foodId);
}
