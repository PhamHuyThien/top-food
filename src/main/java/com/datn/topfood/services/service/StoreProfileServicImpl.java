package com.datn.topfood.services.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.AccountFollow;
import com.datn.topfood.data.model.Food;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.jpa.AccountFollowRepository;
import com.datn.topfood.data.repository.jpa.FoodRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.FileRequest;
import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.FoodDetailResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.StoreWallResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.StoreProfileServic;
import com.datn.topfood.util.ConvertUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.AccountRole;

@Service
public class StoreProfileServicImpl extends BaseService implements StoreProfileServic {

	@Autowired
	FoodRepository foodRepository;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	AccountFollowRepository followRepository;
	
	public final int MAX_SIZE_FOOD = 20;

	@Override
	@Transactional
	public void createFood(FoodRequest foodRequest) {
		Account ime = itMe();
		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		if (foodRepository.countFoodByProfileAccountUsername(ime.getUsername()) >= MAX_SIZE_FOOD) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.MAX_SIZE_FOOD_MESSAGE+MAX_SIZE_FOOD+" món.");
		}
		Food f = new Food(foodRequest.getName(), foodRequest.getPrice(), foodRequest.getContent(), "active",
				profileRepository.findByAccountId(ime.getId()),
				ConvertUtils.convertArrFileReqToSetFile(foodRequest.getFiles()));

		foodRepository.save(f);
	}

	@Override
	public FoodDetailResponse foodDetail(Long foodId) {
		Food f = foodRepository.findById(foodId).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.OTHER_ACTION_IS_DENIED);
		});
		FoodDetailResponse detailResponse = new FoodDetailResponse();
		detailResponse.setContent(f.getContent());
		detailResponse.setFiles(f.getFiles().stream().map((file) -> {
			return new FileRequest(file.getPath(), file.getType().name);
		}).collect(Collectors.toList()));
		detailResponse.setName(f.getName());
		detailResponse.setPrice(f.getPrice());
		return detailResponse;
	}

	@Override
	@Transactional
	public void followStore(Long storeProfileId) {
		Profile p = profileRepository.findByAccountId(storeProfileId);
		if (p == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.OTHER_ACTION_IS_DENIED);
		}
		if (p.getAccount().getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		AccountFollow afl = new AccountFollow();
		afl.setProfile(p);
		afl.setAccount(itMe());
		followRepository.save(afl);
	}

	@Override
	public StoreWallResponse storeProfile(Long storeProfileId) {
		Profile p = profileRepository.findByAccountId(storeProfileId);
		if (p == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.OTHER_ACTION_IS_DENIED);
		}
		StoreWallResponse swr = new StoreWallResponse();
		swr.setAddress(p.getAddress());
		swr.setAvatar(p.getAvatar());
		swr.setBio(p.getBio());
		swr.setCover(p.getCover());
		swr.setFollower(followRepository.countFollowOfProfile(storeProfileId));
		swr.setFoods(foodRepository.findByProfileId(storeProfileId).stream().map((food) -> {
			return new FoodDetailResponse(food.getName(), food.getPrice(), food.getContent(),
					food.getFiles().stream().map((file) -> {
						return new FileRequest(file.getPath(), file.getType().name);
					}).collect(Collectors.toList()));
		}).collect(Collectors.toList()));
		swr.setName(p.getName());
		swr.setStoreId(p.getId());
		return swr;
	}

	@Override
	public PageResponse<StoreWallResponse> listStoreFollow(PageRequest pageRequest) {
		Account ime = itMe();
		Pageable pageable = PageUtils.toPageable(pageRequest);
		Page<AccountFollow> accountFollow = followRepository.listStoreFollow(ime.getUsername(),pageable);
		return new PageResponse<StoreWallResponse>(accountFollow.stream().map((al) -> {
			StoreWallResponse swr = new StoreWallResponse();
			swr.setAddress(al.getProfile().getAddress());
			swr.setAvatar(al.getProfile().getAvatar());
			swr.setBio(al.getProfile().getBio());
			swr.setCover(al.getProfile().getCover());
			swr.setFollower(followRepository.countFollowOfProfile(al.getProfile().getId()));
			swr.setName(al.getProfile().getName());
			swr.setStoreId(al.getProfile().getId());
			return swr;
		}).collect(Collectors.toList()), accountFollow.getTotalElements(), pageRequest.getPageSize());
	}

	@Override
	public void unFollowStore(Long storeId) {
		// TODO Auto-generated method stub
		Account ime = itMe();
		followRepository.delete(
				followRepository.findByAccountUsernameAndProfileId(ime.getUsername(), storeId).orElseThrow(() -> {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.OTHER_ACTION_IS_DENIED);
				}));
	}

	@Override
	public void deleteFood(Long foodId) {
		Account ime = itMe();
		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		foodRepository.delete(foodRepository.findById(foodId).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.FOOD_NOT_EXISTS);
		}));
	}

}
