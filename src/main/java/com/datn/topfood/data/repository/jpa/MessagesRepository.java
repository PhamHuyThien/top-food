package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Messages;
import com.datn.topfood.dto.response.MessagesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    @Query("SELECT new com.datn.topfood.dto.response.MessagesResponse(" +
            "mess.id, " +
            "mess.createAt, " +
            "mess.updateAt, " +
            "mess.message, " +
            "prof " +
            ") FROM Messages mess " +
            "JOIN Conversation con ON mess.conversation = con " +
            "JOIN Participants par ON par.conversation = con " +
            "JOIN Account acc ON par.account = acc " +
            "JOIN Profile prof ON prof.account = acc " +
            "WHERE acc.id = ?1 AND con.id = ?2 AND mess.disableAt IS NULL ")
    Page<MessagesResponse> getListMessages(Long accountId, Long conversationId, Pageable pageable);
}
