package com.datn.topfood.services.service;

import com.datn.topfood.data.model.SubTag;
import com.datn.topfood.data.model.Tag;
import com.datn.topfood.data.repository.jpa.SubTagRepository;
import com.datn.topfood.data.repository.jpa.TagRepository;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.SubTagRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.SubTagResponse;
import com.datn.topfood.dto.response.TitleTagResponse;
import com.datn.topfood.services.interf.SubTagService;
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
public class SubTagServiceImpl implements SubTagService {
  @Autowired
  SubTagRepository subTagRepository;
  @Autowired
  TagRepository tagRepository;
  @Override
  public SubTagResponse createSubTag(SubTagRequest request) {
    boolean ex=subTagRepository.existsBySubTagName(request.getSubTagName());
    if (ex){
      throw new RuntimeException("subtag name already exists "+request.getSubTagName());
    }
    Tag tag= tagRepository.getById(request.getId());
    SubTag subTag=new SubTag();
    subTag.setSubTagName(request.getSubTagName());
    subTag.setTag(tag);
    subTag.setCreateAt(DateUtils.currentTimestamp());
    subTagRepository.save(subTag);
    return new ModelMapper().map(subTag,SubTagResponse.class);
  }

  @Override
  public SubTagResponse updateSubTag(SubTagRequest request, Long id) {
    SubTag subTag=subTagRepository.findById(id).orElseThrow(() -> new RuntimeException("subTag not found"));
    subTag.setUpdateAt(DateUtils.currentTimestamp());
    subTag.setSubTagName(request.getSubTagName());
    subTagRepository.save(subTag);
    return new ModelMapper().map(subTag,SubTagResponse.class);
  }

  @Override
  public SubTagResponse findById(Long id) {
    SubTag subTag=subTagRepository.findById(id).orElseThrow(() -> new RuntimeException("subTag not found"));
    return new ModelMapper().map(subTag,SubTagResponse.class);
  }

  @Override
  public PageResponse<SubTagResponse> searchBySubTagName(String name, PageRequest request) {
    ModelMapper mapper = new ModelMapper();
    Pageable pageable = PageUtils.toPageable(request);
    List<SubTagResponse> subTagResponses = new ArrayList<>();
    name = "%" + name + "%";
    Page<SubTag> subTags = subTagRepository.findBySubTagNameLike(name, pageable);
    for (SubTag x : subTags) {
      SubTagResponse titleTagResponse = mapper.map(x, SubTagResponse.class);
      subTagResponses.add(titleTagResponse);
    }
    PageResponse<SubTagResponse> acc = new PageResponse<>(
            subTagResponses,
            subTags.getTotalElements(),
            pageable.getPageSize()
    );
    return acc;
  }

  @Override
  public void deleteSubTag(Long id) {
    SubTag subTag=subTagRepository.findById(id).orElseThrow(() -> new RuntimeException("subTag not found"));
    subTagRepository.deleteById(subTag.getId());
  }
}

