package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Post;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.model.Report;
import com.datn.topfood.data.model.Rule;
import com.datn.topfood.data.repository.jpa.PostRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.data.repository.jpa.ReportRepository;
import com.datn.topfood.data.repository.jpa.RuleRepository;
import com.datn.topfood.dto.request.ReportRequest;
import com.datn.topfood.dto.response.PostResponse;
import com.datn.topfood.dto.response.ReportResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.ReportService;
import com.datn.topfood.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl extends BaseService implements ReportService {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    RuleRepository ruleRepository;

    @Override
    public void createReport(ReportRequest request) {
        Report report=mapToEntity(request,new Report());
        report.setCreateAt(DateUtils.currentTimestamp());
        reportRepository.save(report);
    }

    @Override
    public List<ReportResponse> getReport() {
        List<Post> posts=postRepository.findAll();
        List<ReportResponse> reportResponses=new ArrayList<>();
        for (Post x: posts){
            if (!x.getReports().isEmpty()){
                ReportResponse reportResponse=new ReportResponse();
                reportResponse.setContent(x.getContent());
                reportResponse.setProfileName(x.getProfile().getName());
                reportResponses.add(reportResponse);
            }
        }
        return reportResponses;
    }

    @Override
    public ReportResponse getReportByStoreId(Long id) {
        return null;
    }

    private Report mapToEntity(ReportRequest request, Report report) {
        Post post = postRepository.findById(request.getIdPost()).orElseThrow(() -> new RuntimeException("post not found"));
        Profile profile = profileRepository.findById(itMe().getId()).orElseThrow(() -> new RuntimeException("profile not found"));
        List<Rule> rules;
        rules = request.getRule().stream().map(ruleRepository :: findByTitle).collect(Collectors.toList());
        report.setRules(rules);
        report.setPost(post);
        report.setProfile(profile);
        return report;
    }

}
