package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.FriendShip;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FriendShipRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.SearchProfileResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.ProfileService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.FriendShipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl extends BaseService implements ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FriendShipRepository friendShipRepository;

    @Override
    public ProfileResponse getFiendProfileByAccountId(Long id) {
        ProfileResponse profileResponse = profileRepository.findFiendProfileByAccountId(id).orElse(null);
        if (profileResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.PROFILE_NOT_EXISTS);
        }
        return profileResponse;
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
    public PageResponse<SearchProfileResponse> search(String search, PageRequest pageRequest) {
        Account itMe = itMe();
        Pageable pageable = PageUtils.toPageable(pageRequest);
        List<SearchProfileResponse> profileResponses = new ArrayList<>();
        search = "%" + search + "%";
        Page<Profile> profilePage = profileRepository.findByNameLikeOrPhoneLike(search, search, itMe.getId(), pageable);
        for (Profile profile : profilePage) {
            SearchProfileResponse searchProfileResponse = new SearchProfileResponse();
            FriendShip friendShip = friendShipRepository.findFriendShipRelation(itMe.getUsername(), profile.getAccount().getUsername());
            if (friendShip != null) {
                if (friendShip.getStatus() == FriendShipStatus.BLOCK) {
                    continue;
                }
                searchProfileResponse.setFriendStatus(friendShip.getStatus());
                if (friendShip.getStatus() == FriendShipStatus.SENDING) {
                    searchProfileResponse.setIsPersonSending(friendShip.getAccountRequest().equals(itMe));
                }
            }
            searchProfileResponse.setAccountId(profile.getAccount().getId());
            searchProfileResponse.setUsername(profile.getAccount().getUsername());
            searchProfileResponse.setPhoneNumber(profile.getAccount().getPhoneNumber());
            searchProfileResponse.setEmail(profile.getAccount().getEmail());
            searchProfileResponse.setProfile(profile);
            searchProfileResponse.setRole(profile.getAccount().getRole());
            profileResponses.add(searchProfileResponse);
        }

        PageResponse<SearchProfileResponse> profileResponsePageResponse = new PageResponse<>(
                profileResponses,
                profilePage.getTotalElements(),
                pageable.getPageSize()
        );
        profileResponsePageResponse.setStatus(true);
        profileResponsePageResponse.setMessage(Message.OTHER_SUCCESS);
        return profileResponsePageResponse;
    }
}