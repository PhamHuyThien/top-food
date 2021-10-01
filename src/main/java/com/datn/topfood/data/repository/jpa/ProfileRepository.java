package com.datn.topfood.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datn.topfood.data.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
