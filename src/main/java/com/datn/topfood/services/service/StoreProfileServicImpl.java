package com.datn.topfood.services.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.File;
import com.datn.topfood.data.model.Food;
import com.datn.topfood.data.repository.jpa.FoodRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.StoreProfileServic;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.FileType;

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
				profileRepository.findByAccountId(ime.getId()), convertArrFileReqToSetFile(foodRequest.getFiles()));
		foodRepository.save(f);
	}
	
	@Override
	public Food foodDetail(Long foodId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<File> convertArrFileReqToSetFile(ArrayList<FoodRequest.File> arrFile){
		Set<File> files = new HashSet<>();
		for(FoodRequest.File f:arrFile) {
			File file  = new File();
			file.setPath(f.getPath());
			file.setType(FileType.valueOf(f.getType().toUpperCase()));
			files.add(file);
		}
		return files;
	}
}
