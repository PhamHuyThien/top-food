package com.datn.topfood.services.service;

import com.datn.topfood.data.model.SubTag;
import com.datn.topfood.data.model.Tag;
import com.datn.topfood.data.repository.jpa.TagRepository;
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

@Service
public class TagServiceImpl implements TagService {
  @Autowired
  TagRepository tagRepository;
  @Override
  public TagResponse createTag(TagRequest request) {
    boolean ex=tagRepository.existsByTagName(request.getTagName());
    if (ex){
      throw new RuntimeException("tag name already exists ");
    }
    ModelMapper mapper=new ModelMapper();
    Tag tag=mapper.map(request, Tag.class);
    tag.setCreateAt(DateUtils.currentTimestamp());
    tagRepository.save(tag);
    return mapper.map(tag,TagResponse.class);
  }

  @Override
  public TagResponse updateTag(TagRequest request,Long id) {
    Tag tag=tagRepository.findById(id).orElseThrow(() -> new RuntimeException("tag not found"));
    tag.setTagName(request.getTagName());
    tag.setUpdateAt(DateUtils.currentTimestamp());
    tagRepository.save(tag);
    return new ModelMapper().map(tag,TagResponse.class);
  }

  @Override
  public TagResponse findById(Long id) {
    ModelMapper mapper=new ModelMapper();
    List<SubTagResponse> subTagResponses=new ArrayList<>();
    Tag tag=tagRepository.findById(id).orElseThrow(() -> new RuntimeException("tag not found"));
    for (SubTag x:tag.getSubTags()){
      SubTagResponse subTagResponse=mapper.map(x,SubTagResponse.class);
      subTagResponses.add(subTagResponse);
    }
    TagResponse tagResponse=mapper.map(tag,TagResponse.class);;
    tagResponse.setSubTagResponses(subTagResponses);
    return tagResponse;
  }



  @Override
  public PageResponse<TitleTagResponse> searchByTagName(String tagName, PageRequest request) {
    ModelMapper mapper = new ModelMapper();
    Pageable pageable = PageUtils.toPageable(request);
    List<TitleTagResponse> titleTagResponses = new ArrayList<>();
    tagName = "%" + tagName + "%";
    Page<Tag> tags = tagRepository.findByTagNameLike(tagName, pageable);
    for (Tag x : tags) {
      TitleTagResponse titleTagResponse = mapper.map(x, TitleTagResponse.class);
      titleTagResponses.add(titleTagResponse);
    }
    PageResponse<TitleTagResponse> acc = new PageResponse<>(
            titleTagResponses,
            tags.getTotalElements(),
            pageable.getPageSize()
    );
    return acc;
  }
}
