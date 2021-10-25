package com.datn.topfood.data.seeders;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Favorite;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FavoriteRepository;
import com.datn.topfood.data.repository.jpa.TagRepository;
import com.datn.topfood.util.BeanUtils;

public class FavoriteSeeder implements Seeder {
    @Override
    public void seed() {
        AccountRepository accountRepository = BeanUtils.getBean(AccountRepository.class);
        FavoriteRepository favoriteRepository = BeanUtils.getBean(FavoriteRepository.class);
        TagRepository tagRepository = BeanUtils.getBean(TagRepository.class);
        Account thien = accountRepository.findById(1L).orElse(null);
        Favorite[] favorites = new Favorite[]{
                new Favorite(1L, 1, tagRepository.findById(1L).orElse(null), thien),
                new Favorite(2L, 1, tagRepository.findById(2L).orElse(null), thien),
                new Favorite(3L, 1, tagRepository.findById(3L).orElse(null), thien),
                new Favorite(4L, 1, tagRepository.findById(4L).orElse(null), thien),
                new Favorite(5L, 1, tagRepository.findById(5L).orElse(null), thien)
        };
        for (Favorite favorite : favorites) {
            favoriteRepository.save(favorite);
        }
    }
}
