package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.File;
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
public class CommentResponse {
    String content;
    List<File> files;
    ProfileResponse profile;
}
