package com.datn.topfood.data.repository.jpa;

import com.datn.topfood.data.model.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
}
