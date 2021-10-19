package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachments, Long> {
    @Query("SELECT atm FROM Attachments atm " +
            "WHERE atm.messages.id = ?1 ")
    List<Attachments> findByMessagesId(Long messageId);
}
