package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Page<Favorite> findByAccountEquals(Account account, Pageable pageable);
    void deleteAllByAccountEquals(Account account);
}
