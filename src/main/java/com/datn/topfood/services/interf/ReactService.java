package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.dto.request.CommentPostRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ReactionRequest;
import com.datn.topfood.dto.response.CommentResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.ReactionResponse;

public interface ReactService {
    Void commentPost(Long id, CommentPostRequest commentPostRequest, Account itMe);

    Void reactPost(Long id, ReactionRequest reactPostRequest, Account itMe);

    PageResponse<ReactionResponse> listReactionPost(Long id, PageRequest pageRequest, Account itMe);

    PageResponse<CommentResponse> listCommentPost(Long id, PageRequest pageRequest, Account itMe);

    Void reactionComment(Long id, ReactionRequest reactionRequest, Account itMe);

    Void commentReply(Long id, CommentPostRequest commentPostRequest, Account itMe);

    PageResponse<CommentResponse> listCommentReply(Long id, PageRequest pageRequest, Account itMe);

    PageResponse<ReactionResponse> listReactionComment(Long id, PageRequest pageRequest, Account itMe);

    Void deleteCommentPost(Long commentId, Account itMe);
}
