package com.datn.topfood.controllers;

import com.datn.topfood.dto.response.FileUploadResponse;
import com.datn.topfood.dto.response.Response;
import com.datn.topfood.services.interf.FileService;
import com.datn.topfood.util.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

}
