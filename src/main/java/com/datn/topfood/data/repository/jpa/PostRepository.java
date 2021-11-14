package com.datn.topfood.data.repository.jpa;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

	@Query("select p from Post as p where p.profile.account.username = ?1 and p.id = ?2")
	public Optional<Post> findByAccountAndPostId(String username,Long id);
	
	@Query("select p from Post as p where p.profile.account.id = ?1")
	public Page<Post> findAllByAccount(Long accountId,Pageable pageable);
}
