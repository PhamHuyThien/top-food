package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
  boolean existsByTagName(String tagName);
  Optional<Tag> findByTagName(String tagName);
  Page<Tag> findByEnableAndTagNameLike(boolean enable,String tagName, Pageable request);
  @Query("select t from Tag as t where t.id IN ?1")
  List<Tag> findAllListTagId(Long[] longs);
}
