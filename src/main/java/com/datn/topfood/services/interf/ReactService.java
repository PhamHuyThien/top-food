package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ReactionRequest;
import com.datn.topfood.dto.response.CommentResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;

public interface ReactService {
    Void commentPost(Long id, CommentPostRequest commentPostRequest, Account itMe);

    Void reactPost(Long id, ReactionRequest reactPostRequest, Account itMe);

    PageResponse<ProfileResponse> listReactionPost(Long id, PageRequest pageRequest, Account itMe);

    PageResponse<CommentResponse> listCommentPost(Long id, PageRequest pageRequest, Account itMe);

    Void reactionComment(Long id, ReactionRequest reactionRequest, Account itMe);
}
