package com.datn.topfood.data.repository.jpa;


import com.datn.topfood.data.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepsitory extends JpaRepository<Conversation, Long> {
}
