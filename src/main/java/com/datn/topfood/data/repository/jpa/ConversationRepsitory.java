package com.datn.topfood.data.repository.jpa;


import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.dto.response.ConversationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConversationRepsitory extends JpaRepository<Conversation, Long> {

    @Query("SELECT con FROM Conversation con " +
            "JOIN Participants par ON par.conversation = con " +
            "JOIN Account acc ON par.account = acc " +
            "WHERE acc.id = ?1 AND con.id = ?2")
    Conversation findByIdFromAccountId(Long profileId, Long conversationId);

    @Query("SELECT new com.datn.topfood.dto.response.ConversationResponse( " +
            "con.id, " +
            "con.title," +
            "con.createAt, " +
            "con.updateAt," +
            "prof " +
            ") FROM Conversation con " +
            "JOIN Participants par ON con = par.conversation " +
            "JOIN Account acc ON par.account = acc " +
            "JOIN Profile prof ON acc = prof.account " +
            "WHERE acc.id = ?1 AND con.disableAt IS NULL AND par.disableAt IS NULL ")
    Page<ConversationResponse> getAllConversation(Long accountId, Pageable pageable);
}
