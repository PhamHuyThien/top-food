package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Profile;
import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.request.SearchFriendRequest;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.SearchFriendResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileService {
  void createProfile(RegisterRequest create);
  ProfileResponse updateProfile(ProfileRequest request,Long id);
  ProfileResponse findById(Long id);
  ProfileResponse findByName(String name);
  List<ProfileResponse> SearchByNameAndPhone(String name);
}
