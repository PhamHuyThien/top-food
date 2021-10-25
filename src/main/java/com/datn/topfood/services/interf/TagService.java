package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.TagRequest;
import com.datn.topfood.dto.response.AccountResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.TagResponse;
import com.datn.topfood.dto.response.TitleTagResponse;

import java.util.List;

public interface TagService {
    TagResponse createTag(TagRequest request);

    TagResponse updateTag(TagRequest request, Long id);

    TagResponse findById(Long id);

    PageResponse<TitleTagResponse> searchByTagName(boolean enable, String phoneNumber, PageRequest request);

    void deleteTag(Long id);

    void updateEnable(Long id);
}
