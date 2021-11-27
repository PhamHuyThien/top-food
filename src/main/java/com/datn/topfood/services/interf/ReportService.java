package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.ReportRequest;
import com.datn.topfood.dto.response.ReportResponse;

import java.util.List;

public interface ReportService {
    void createReport(ReportRequest request);
    List<ReportResponse> getReport();
    ReportResponse getReportByStoreId(Long id);
}
