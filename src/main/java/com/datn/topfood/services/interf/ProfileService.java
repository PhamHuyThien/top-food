package com.datn.topfood.services.interf;

import com.datn.topfood.data.model.Tag;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.ProfileRequest;
import com.datn.topfood.dto.response.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProfileService {
    ProfileResponse getFiendProfileByAccountId(Long id);

    void updateProfile(ProfileRequest profileRequest);

    PageResponse<SearchProfileResponse> search(String search, PageRequest pageRequest);

    PageResponse<? extends Tag> getFavorite(PageRequest pageRequest);

    void updateFavorite(Set<Long> listIdTag);
}
