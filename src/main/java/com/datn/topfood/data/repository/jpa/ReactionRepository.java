package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
