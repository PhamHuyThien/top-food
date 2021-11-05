package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.request.CommentPostRequest;

public interface ReactService {
    Void commentPost(CommentPostRequest commentPostRequest, Account itMe);
}
