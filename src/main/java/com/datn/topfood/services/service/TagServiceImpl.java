package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Tag;
import com.datn.topfood.data.repository.jpa.TagRepository;
import com.datn.topfood.dto.request.TagRequest;
import com.datn.topfood.dto.response.TagResponse;
import com.datn.topfood.dto.response.TitleTagResponse;
import com.datn.topfood.services.interf.TagService;
import com.datn.topfood.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    Tag tag=tagRepository.findById(id).orElseThrow(() -> new RuntimeException("tag not found"));
    TagResponse tagResponse=new TagResponse();
    tagResponse.setSubTagResponses(tag.getSubTags());
    return tagResponse;
   // return new ModelMapper().map(tag,TagResponse.class);
  }

  @Override
  public TagResponse findByName(String name) {
    return null;
  }

  @Override
  public List<TitleTagResponse> getAllTitleTag() {
    ModelMapper mapper=new ModelMapper();
    List<Tag> tags=tagRepository.findAll();
    List<TitleTagResponse> titleTagResponses=null;
    for (Tag x:tags){
      TitleTagResponse tagResponse=mapper.map(x,TitleTagResponse.class);
      titleTagResponses.add(tagResponse);
    }
    return titleTagResponses;
  }
}
