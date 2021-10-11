package com.datn.topfood.services.service;

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
import com.datn.topfood.util.DateUtils;
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
		if (foodRequest.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		if (foodRepository.countFoodByProfileAccountUsername(ime.getUsername()) >= MAX_SIZE_FOOD) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.MAX_SIZE_FOOD_MESSAGE + MAX_SIZE_FOOD + " món.");
		}
		Food f = new Food(foodRequest.getName(), foodRequest.getPrice(), foodRequest.getContent(), "active",
				profileRepository.findByAccountId(ime.getId()),
				ConvertUtils.convertArrFileReqToSetFile(foodRequest.getFiles()));
		f.setCreateAt(DateUtils.currentTimestamp());
		foodRepository.save(f);
	}

	@Override
	public FoodDetailResponse foodDetail(Long foodId) {
		Food f = foodRepository.findById(foodId).orElse(null);
		if (f == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.OTHER_ACTION_IS_DENIED);
		}
		FoodDetailResponse detailResponse = new FoodDetailResponse();
		detailResponse.setContent(f.getContent());
		detailResponse.setFiles(f.getFiles().stream().map((file) -> {
			return new FileRequest(file.getPath(), file.getType().name);
		}).collect(Collectors.toList()));
		detailResponse.setId(f.getId());
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
			return new FoodDetailResponse(food.getId(), food.getName(), food.getPrice(), food.getContent(),
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
		Page<AccountFollow> accountFollow = followRepository.listStoreFollow(ime.getUsername(), pageable);
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
	@Transactional
	public void unFollowStore(Long storeId) {
		Account ime = itMe();
		AccountFollow accountFollow = followRepository.findByAccountUsernameAndProfileId(ime.getUsername(), storeId)
				.orElse(null);
		if (accountFollow == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.OTHER_ACTION_IS_DENIED);
		}
		followRepository.delete(accountFollow);
	}

	@Override
	@Transactional
	public void deleteFood(Long foodId) {
		Account ime = itMe();
		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		// kiểm tra món ăn có phải đúng của cửa hàng này hay không
		if (foodRepository.findByIdAndAccountUsername(foodId, ime.getUsername()) == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		Food food = foodRepository.findById(foodId).orElse(null);
		if (food == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.FOOD_NOT_EXISTS);
		}
		foodRepository.delete(food);
	}

	@Override
	public PageResponse<FoodDetailResponse> listFood(PageRequest pageRequest) {
		Account ime = itMe();
		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		Pageable pageable = PageUtils.toPageable(pageRequest);
		Page<Food> foods = foodRepository.findByAccountUsername(ime.getUsername(), pageable);
		return new PageResponse<FoodDetailResponse>(foods.stream().map((food) -> {
			return new FoodDetailResponse(food.getId(), food.getName(), food.getPrice(), food.getContent(),
					food.getFiles().stream().map((file) -> {
						return new FileRequest(file.getPath(), file.getType().name);
					}).collect(Collectors.toList()));
		}).collect(Collectors.toList()), foods.getTotalElements(), pageRequest.getPageSize());
	}

	@Override
	public FoodDetailResponse updateFood(FoodRequest foodRequest) {
		Account ime = itMe();
		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		// kiểm tra món ăn có phải đúng của cửa hàng này hay không
		if (foodRepository.findByIdAndAccountUsername(foodRequest.getId(), ime.getUsername()) == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		Food food = foodRepository.findById(foodRequest.getId()).orElse(null);
		if (food == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.FOOD_NOT_EXISTS);
		}
		food.setUpdateAt(DateUtils.currentTimestamp());
		food.setContent(foodRequest.getContent());
		food.setFiles(ConvertUtils.convertArrFileReqToSetFile(foodRequest.getFiles()));
		food.setName(foodRequest.getName());
		food.setPrice(foodRequest.getPrice());
		food = foodRepository.save(food);

		return new FoodDetailResponse(food.getId(), food.getName(), food.getPrice(), food.getContent(),
				food.getFiles().stream().map((file) -> {
					return new FileRequest(file.getPath(), file.getType().name);
				}).collect(Collectors.toList()));
	}
}