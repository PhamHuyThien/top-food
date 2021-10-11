package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.SubTagRequest;
import com.datn.topfood.dto.response.SubTagResponse;

public interface SubTagService {
  SubTagResponse createSubTag(SubTagRequest request);

  SubTagResponse updateSubTag(SubTagRequest request, Long id);

  SubTagResponse findById(Long id);

  SubTagResponse findByName(String name);
}
