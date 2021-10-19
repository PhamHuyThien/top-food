package com.datn.topfood.services.interf;

import com.datn.topfood.dto.response.FileUploadResponse;
import com.datn.topfood.dto.response.PageResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileUploadResponse upload(MultipartFile multipartFile);

    PageResponse<FileUploadResponse> upload(MultipartFile[] multipartFiles);

    ResponseEntity<Resource> get(String fileName);
}
