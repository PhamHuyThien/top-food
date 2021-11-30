package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
