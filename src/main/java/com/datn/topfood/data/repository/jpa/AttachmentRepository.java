package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachments, Long> {
}
