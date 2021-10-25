package com.datn.topfood.dto.response;

import com.datn.topfood.dto.request.FileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodResponse {
    Long id;
    String name;
    Double price;
    String content;
    List<FileRequest> files;
    String storeName;

}
