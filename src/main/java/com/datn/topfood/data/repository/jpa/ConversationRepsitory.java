package com.datn.topfood.data.repository.jpa;


import com.datn.topfood.data.model.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ConversationRepsitory extends JpaRepository<Conversation, Long> {
    @Query("SELECT con FROM Conversation con " +
            "JOIN Participants p ON p.conversation = con " +
            "WHERE p.account.id = ?1")
    Page<Conversation> listConversation(Long accountId, Pageable pageable);
}
