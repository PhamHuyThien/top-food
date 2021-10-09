package com.datn.topfood.data.repository.jpa;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.datn.topfood.data.model.Profile;
import com.datn.topfood.dto.response.ProfileResponse;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select new com.datn.topfood.dto.response.ProfileResponse(" +
            "acc.id, " +
            "acc.phoneNumber, " +
            "acc.email, " +
            "acc.role, " +
            "prof " +
            ") " +
            "from Profile prof " +
            "JOIN Account acc ON prof.account = acc " +
            "WHERE acc.id = ?1")
    Optional<ProfileResponse> findFiendProfileByAccountId(Long id);

    Profile findByAccountId(long profileId);

    @Query("SELECT prof FROM Profile prof " +
            "JOIN Account acc ON prof.account = acc " +
            "WHERE prof.name LIKE ?1 OR acc.phoneNumber LIKE ?2 ")
    Page<Profile> findByNameLikeOrPhoneLike(String name, String phone, Pageable pageable);
}