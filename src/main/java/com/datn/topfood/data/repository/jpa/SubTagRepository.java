package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.SubTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTagRepository extends JpaRepository<SubTag,Long> {
  boolean existsBySubTagName(String subTagName);
}
