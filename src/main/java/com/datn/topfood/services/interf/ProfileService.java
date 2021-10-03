package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Profile;
import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.ProfileResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileService {
  void createProfile(RegisterRequest create);
  ProfileResponse updateProfile(ProfileRequest request,Long id);
  ProfileResponse findById(Long id);
  ProfileResponse findByName(String name);
}
