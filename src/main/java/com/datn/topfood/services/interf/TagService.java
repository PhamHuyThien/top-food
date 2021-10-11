package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.TagRequest;
import com.datn.topfood.dto.response.TagResponse;
import com.datn.topfood.dto.response.TitleTagResponse;

import java.util.List;

public interface TagService {
  TagResponse createTag(TagRequest request);
  TagResponse updateTag(TagRequest request,Long id);
  TagResponse findById(Long id);
  TagResponse findByName(String name);
  List<TitleTagResponse> getAllTitleTag();
}
