package com.datn.topfood.services.service;

import com.datn.topfood.data.model.SubTag;
import com.datn.topfood.data.model.Tag;
import com.datn.topfood.data.repository.jpa.SubTagRepository;
import com.datn.topfood.data.repository.jpa.TagRepository;
import com.datn.topfood.dto.request.SubTagRequest;
import com.datn.topfood.dto.response.SubTagResponse;
import com.datn.topfood.services.interf.SubTagService;
import com.datn.topfood.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    return null;
  }

  @Override
  public SubTagResponse findById(Long id) {
    return null;
  }

  @Override
  public SubTagResponse findByName(String name) {
    return null;
  }
}
