package com.datn.topfood.data.repository.jpa;


import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.dto.response.ConversationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConversationRepsitory extends JpaRepository<Conversation, Long> {

    @Query("SELECT new com.datn.topfood.dto.response.ConversationResponse( " +
            "con.title," +
            "con.createAt," +
            "prof " +
            ") FROM Conversation con " +
            "JOIN Participants par ON con = par.conversation " +
            "JOIN Account acc ON par.account = acc " +
            "JOIN Profile prof ON acc = prof.account " +
            "WHERE acc.id = ?1 AND con.disableAt IS NULL")
    Page<ConversationResponse> getAllConversation(Long accountId, Pageable pageable);
}
