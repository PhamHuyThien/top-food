package com.datn.topfood.controllers;

import com.datn.topfood.dto.response.FileUploadResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.FileService;
import com.datn.topfood.util.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileService fileService;

    @GetMapping("/{fileName}")
    public ResponseEntity get(@PathVariable("fileName") String fileName) {
        return fileService.get(fileName);
    }

    @PostMapping("/upload")
    public ResponseEntity<Response<FileUploadResponse>> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(new Response<>(true, Message.OTHER_SUCCESS, fileService.upload(file)));
    }

    @PostMapping("/uploads")
    public ResponseEntity<PageResponse<FileUploadResponse>> uploadFile(@RequestParam("file") MultipartFile[] files) {
        List<FileUploadResponse> fileUploadResponseList = Arrays.asList(files)
                .stream()
                .map(file -> fileService.upload(file))
                .collect(Collectors.toList());
        PageResponse<FileUploadResponse> pageResponse = new PageResponse<>(
                fileUploadResponseList,
                fileUploadResponseList.size(),
                fileUploadResponseList.size()
        );
        pageResponse.setStatus(true);
        pageResponse.setMessage(Message.OTHER_SUCCESS);
        return ResponseEntity.ok(pageResponse);
    }

}
