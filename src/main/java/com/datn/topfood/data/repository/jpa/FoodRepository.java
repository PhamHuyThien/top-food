package com.datn.topfood.data.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

	@Query("select f from Food as f where f.profile.id = ?1 ")
	List<Food> findByProfileId(Long profileId);
	
	@Query("select count(f) from Food as f where f.profile.account.username = ?1 ")
	long countFoodByProfileAccountUsername(String username);
	
	@Query("select f from Food as f where f.id = ?1 and f.profile.account.username = ?2")
	Food findByIdAndAccountUsername(Long foodId,String username);
	
	@Query("select f from Food as f where f.profile.account.username = ?1 AND f.disableAt IS NULL")
	Page<Food> findByAccountUsername(String username,Pageable pageable);
}
