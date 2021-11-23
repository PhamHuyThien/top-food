package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Base;
import com.datn.topfood.data.model.File;
import com.datn.topfood.util.enums.ReactType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentResponse extends Base {
    String content;
    List<File> files;
    ProfileResponse profile;
    Long totalReaction;
    ReactType itMeReaction;
}
