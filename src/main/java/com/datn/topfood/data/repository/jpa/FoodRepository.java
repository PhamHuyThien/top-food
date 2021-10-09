package com.datn.topfood.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn.topfood.data.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

	
}
