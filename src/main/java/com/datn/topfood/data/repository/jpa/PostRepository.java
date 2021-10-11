package com.datn.topfood.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn.topfood.data.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
