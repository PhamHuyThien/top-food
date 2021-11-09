package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.request.ReactPostRequest;

public interface ReactService {
    Void commentPost(Long id, CommentPostRequest commentPostRequest, Account itMe);

    Void reactPost(Long id, ReactPostRequest reactPostRequest, Account itMe);
}
