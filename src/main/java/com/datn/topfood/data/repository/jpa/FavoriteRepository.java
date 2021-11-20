package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Favorite;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Page<Favorite> findByAccountEquals(Account account, Pageable pageable);
    void deleteAllByAccountEquals(Account account);
    @Query("select f from Favorite as f where f.account.id = ?1")
    List<Favorite> findByAccountId(Long accountId);
}
