package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

}
