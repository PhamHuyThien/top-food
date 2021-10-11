package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
  boolean existsByTagName(String tagName);
  Optional<Tag> findByTagName(String tagName);
}
