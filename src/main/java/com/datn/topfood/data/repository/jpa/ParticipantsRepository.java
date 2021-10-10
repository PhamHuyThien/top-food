package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.data.model.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
    @Query("SELECT par FROM Participants par " +
            "JOIN Conversation con ON par.conversation = con " +
            "JOIN Account acc ON par.account = acc " +
            "WHERE acc.id = ?1 AND con.id = ?2 AND par.disableAt IS NULL AND con.disableAt IS NULL")
    Participants getParticipantsFromAccount(Long accountId, Long conversationId);

    Participants findByConversationAndAccount(Conversation conversation, Account account);
}
