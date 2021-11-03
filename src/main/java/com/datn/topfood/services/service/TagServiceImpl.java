package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Food;
import com.datn.topfood.data.model.Tag;
import com.datn.topfood.data.repository.jpa.TagRepository;
import com.datn.topfood.dto.request.FileRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.TagRequest;
import com.datn.topfood.dto.response.*;
import com.datn.topfood.services.interf.TagService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public TagResponse createTag(TagRequest request) {
        boolean ex = tagRepository.existsByTagName(request.getTagName());
        if (ex) {
            throw new RuntimeException("tag name already exists ");
        }
        ModelMapper mapper = new ModelMapper();
        Tag tag = mapper.map(request, Tag.class);
        tag.setImage(request.getImage());
        tag.setCreateAt(DateUtils.currentTimestamp());
        tagRepository.save(tag);
        return mapper.map(tag, TagResponse.class);
    }

    @Override
    public TagResponse updateTag(TagRequest request, Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("tag not found"));
        tag.setTagName(request.getTagName());
        tag.setUpdateAt(DateUtils.currentTimestamp());
        tag.setImage(request.getImage());
        tagRepository.save(tag);
        return new ModelMapper().map(tag, TagResponse.class);
    }

    @Override
    public TagResponse findById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("tag not found"));
        TagResponse tagResponse = new TagResponse();
        tagResponse.setTagName(tag.getTagName());
        tagResponse.setCreateAt(tag.getCreateAt());
        tagResponse.setUpdateAt(tag.getUpdateAt());
        List<FoodResponse> foodResponses = new ArrayList<>();
        for (Food x : tag.getFood()) {
            FoodResponse foodResponse = new FoodResponse();
            foodResponse.setId(x.getId());
            foodResponse.setName(x.getName());
            foodResponse.setPrice(x.getPrice());
            foodResponse.setStoreName(x.getProfile().getName());
            foodResponse.setFiles(x.getFiles().stream().map((file) -> {
                return file.getPath();
            }).collect(Collectors.toList()));
            foodResponse.setContent(x.getContent());
            foodResponses.add(foodResponse);

        }
        tagResponse.setFoods(foodResponses);
        return tagResponse;
    }


    @Override
    public PageResponse<TitleTagResponse> searchByTagName(boolean enable, String tagName, PageRequest request) {
        ModelMapper mapper = new ModelMapper();
        Pageable pageable = PageUtils.toPageable(request);
        List<TitleTagResponse> titleTagResponses = new ArrayList<>();
        tagName = "%" + tagName + "%";
        Page<Tag> tags = tagRepository.findByEnableAndTagNameLike(enable, tagName, pageable);
        for (Tag x : tags) {
            TitleTagResponse titleTagResponse = new TitleTagResponse();
            titleTagResponse.setId(x.getId());
            titleTagResponse.setTagName(x.getTagName());
            titleTagResponse.setImage(x.getImage());
            titleTagResponse.setEnable(x.isEnable());
            titleTagResponses.add(titleTagResponse);
        }
        PageResponse<TitleTagResponse> acc = new PageResponse<>(
                titleTagResponses,
                tags.getTotalElements(),
                pageable.getPageSize()
        );
        return acc;
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("tag not found"));
        tagRepository.deleteById(tag.getId());
    }

    @Override
    public void updateEnable(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("tag not found"));
        tag.setEnable(!tag.isEnable());
        tagRepository.save(tag);
    }
}
