package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.ProfileService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl extends BaseService implements ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public ProfileResponse getFiendProfileByAccountId(Long id) {
        return profileRepository.findFiendProfileByAccountId(id).orElseThrow(()->{
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND,Message.PROFILE_NOT_EXISTS);
        });
    }

    @Override
    public void updateProfile(ProfileRequest profileRequest) {
        Account itMe = itMe();
        Profile profile = profileRepository.findByAccountId(itMe.getId());
        profile.setName(profileRequest.getName());
        profile.setAddress(profileRequest.getAddress());
        profile.setBirthday(profileRequest.getBirthday());
        profile.setBio(profileRequest.getBio());
        profile.setCover(profileRequest.getCover());
        profile.setAvatar(profileRequest.getAvatar());
        profile.setUpdateAt(DateUtils.currentTimestamp());
        profileRepository.save(profile);
    }

    @Override
    public PageResponse<ProfileResponse> search(String search, PageRequest pageRequest) {
        Pageable pageable = PageUtils.toPageable(pageRequest);
        List<ProfileResponse> profileResponses = new ArrayList<>();
        search = "%" + search + "%";
        Page<Profile> profilePage = profileRepository.findByNameLikeOrPhoneLike(search, search, pageable);
        for (Profile profile : profilePage) {
            ProfileResponse profileResponse = new ProfileResponse();
            profileResponse.setAccountId(profile.getAccount().getId());
            profileResponse.setPhoneNumber(profile.getAccount().getPhoneNumber());
            profileResponse.setEmail(profile.getAccount().getEmail());
            profileResponse.setProfile(profile);
            profileResponses.add(profileResponse);
        }
        PageResponse<ProfileResponse> profileResponsePageResponse = new PageResponse<>(
                profileResponses,
                profilePage.getTotalElements(),
                pageable.getPageSize()
        );
        profileResponsePageResponse.setStatus(true);
        profileResponsePageResponse.setMessage(Message.OTHER_SUCCESS);
        return profileResponsePageResponse;
    }
}
