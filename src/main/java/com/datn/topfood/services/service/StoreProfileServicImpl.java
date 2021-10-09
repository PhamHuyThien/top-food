package com.datn.topfood.services.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Food;
import com.datn.topfood.data.repository.jpa.FoodRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.FileRequest;
import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.response.FoodDetailResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.StoreProfileServic;
import com.datn.topfood.util.ConvertUtils;
import com.datn.topfood.util.constant.Message;

@Service
public class StoreProfileServicImpl extends BaseService implements StoreProfileServic {

	@Autowired
	FoodRepository foodRepository;
	@Autowired
	ProfileRepository profileRepository;

	@Override
	@Transactional
	public void createFood(FoodRequest foodRequest) {
		Account ime = itMe();
//		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0)
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		Food f = new Food(foodRequest.getName(), foodRequest.getPrice(), foodRequest.getContent(), "active",
				profileRepository.findByAccountId(ime.getId()),
				ConvertUtils.convertArrFileReqToSetFile(foodRequest.getFiles()));
		foodRepository.save(f);
	}

	@Override
	public FoodDetailResponse foodDetail(Long foodId) {
		// TODO Auto-generated method stub
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

}
