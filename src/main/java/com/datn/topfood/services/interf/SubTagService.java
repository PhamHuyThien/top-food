package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.SubTagRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.SubTagResponse;
import org.springframework.data.domain.Page;

public interface SubTagService {
    SubTagResponse createSubTag(SubTagRequest request);

    SubTagResponse updateSubTag(SubTagRequest request, Long id);

    SubTagResponse findById(Long id);

    PageResponse<SubTagResponse> searchBySubTagName(String name, PageRequest request);

    void deleteSubTag(Long id);
}
