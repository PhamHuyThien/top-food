package com.datn.topfood.data.seeders;

import com.datn.topfood.data.model.Tag;
import com.datn.topfood.data.repository.jpa.TagRepository;
import com.datn.topfood.util.BeanUtils;

public class TagSeeder implements Seeder {
    @Override
    public void seed() {
        TagRepository tagRepository = BeanUtils.getBean(TagRepository.class);
        Tag[] tags = new Tag[]{
                new Tag(1L, "Phở"),
                new Tag(2L, "Bún"),
                new Tag(3L, "Mì tôm"),
                new Tag(4L, "Cơm"),
                new Tag(5L, "Đùi gà"),
                new Tag(6L, "Trà"),
        };
        for (Tag tag : tags) {
            tagRepository.save(tag);
        }
    }
}
