package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.services.interf.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
  @Autowired
  ProfileRepository profileRepository;
  @Autowired
  AccountRepository accountRepository;

  @Override
  public void createProfile(RegisterRequest create) {
    Account account =accountRepository.findByPhoneNumber(create.getPhoneNumber());
    ModelMapper mapper = new ModelMapper();
    Profile profile = mapper.map(create, Profile.class);
    profile.setAccount(account);
    profile.setAvatar("https://fakeimg.pl/400x400/ff0000/000");
    profileRepository.save(profile);
  }

  @Override
  public ProfileResponse updateProfile(ProfileRequest request,Long id) {
    Profile profile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("profile not found" + id));
    profile.setName(request.getName());
    profile.setAddress(request.getAddress());
    profile.setAge(request.getAge());
    profile.setBio(request.getBio());
    profile.setCover(request.getCover());
    profileRepository.save(profile);
    return new ModelMapper().map(profile, ProfileResponse.class);
  }

  @Override
  public ProfileResponse findById(Long id) {
    Profile profile = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("profile not found" + id));
    return new ModelMapper().map(profile, ProfileResponse.class);
  }

  @Override
  public ProfileResponse findByName(String name) {
    Profile profile=profileRepository.findByName(name).orElseThrow(()->new RuntimeException("profile not found" +name));
    return new ModelMapper().map(profile,ProfileResponse.class);
  }
}
