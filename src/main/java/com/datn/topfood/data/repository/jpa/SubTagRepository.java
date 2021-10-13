package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.SubTag;
import com.datn.topfood.data.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTagRepository extends JpaRepository<SubTag,Long> {
  boolean existsBySubTagName(String subTagName);
  Page<SubTag> findBySubTagNameLike(String tagName, Pageable request);
}
