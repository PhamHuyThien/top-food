package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ReactPostRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;

public interface ReactService {
    Void commentPost(Long id, CommentPostRequest commentPostRequest, Account itMe);

    Void reactPost(Long id, ReactPostRequest reactPostRequest, Account itMe);

    PageResponse<ProfileResponse> listReactionPost(Long id, PageRequest pageRequest, Account itMe);
}
