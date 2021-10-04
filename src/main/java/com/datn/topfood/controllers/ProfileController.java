package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.services.interf.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/profile")
public class ProfileController {
  @Autowired
  ProfileService profileService;
  @PostMapping(path = "")
  public void createProfile(@RequestBody RegisterRequest create){
    profileService.createProfile(create);
  }
  @PutMapping("/{id}")
  public ProfileResponse updateProfile(@RequestBody ProfileRequest update,@PathVariable Long id){
    return profileService.updateProfile(update,id);
  }
  @GetMapping("/{id}")
  public ProfileResponse findById(@PathVariable Long id){
    return profileService.findById(id);
  }
  @GetMapping(path = "")
  public List<ProfileResponse> search(@RequestParam String name){
    return profileService.SearchByNameAndPhone(name);
  }
}
