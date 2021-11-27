package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.ReportRequest;
import com.datn.topfood.dto.response.ReportResponse;
import com.datn.topfood.services.interf.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @PostMapping(path = "")
    public void createReport(@RequestBody ReportRequest request) {
        reportService.createReport(request);
    }
    @GetMapping(path = "")
    public List<ReportResponse> get(){
        return reportService.getReport();
    }
}
