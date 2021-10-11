package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule,Long> {
  boolean existsByTitle(String title);
}
