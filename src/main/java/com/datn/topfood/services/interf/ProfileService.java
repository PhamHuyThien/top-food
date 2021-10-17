package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.request.RegisterRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileService {
    ProfileResponse getFiendProfileByAccountId(Long id);

    void updateProfile(ProfileRequest profileRequest);

    PageResponse<ProfileResponse> search(String search, PageRequest pageRequest);
}
