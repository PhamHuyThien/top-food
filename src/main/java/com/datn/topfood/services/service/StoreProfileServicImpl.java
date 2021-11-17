package com.datn.topfood.services.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.AccountFollow;
import com.datn.topfood.data.model.Food;
import com.datn.topfood.data.model.Post;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.model.Reaction;
import com.datn.topfood.data.model.ReactionFood;
import com.datn.topfood.data.repository.custom.impl.StoreCustomRepository;
import com.datn.topfood.data.repository.jpa.AccountFollowRepository;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FoodRepository;
import com.datn.topfood.data.repository.jpa.PostRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.data.repository.jpa.ReactionFoodRepo;
import com.datn.topfood.data.repository.jpa.ReactionRepository;
import com.datn.topfood.data.repository.jpa.TagRepository;
import com.datn.topfood.dto.request.FileRequest;
import com.datn.topfood.dto.request.FoodReactionRequest;
import com.datn.topfood.dto.request.FoodRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.PostRequest;
import com.datn.topfood.dto.request.SearchFoodsRequest;
import com.datn.topfood.dto.response.FoodDetailResponse;
import com.datn.topfood.dto.response.FoodResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.PostResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.dto.response.SimpleAccountResponse;
import com.datn.topfood.dto.response.StoreWallResponse;
import com.datn.topfood.dto.response.TagResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.StoreProfileServic;
import com.datn.topfood.util.ConvertUtils;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.ReactType;

@Service
public class StoreProfileServicImpl extends BaseService implements StoreProfileServic {

	@Autowired
	FoodRepository foodRepository;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	AccountFollowRepository followRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	StoreCustomRepository storeCustomRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	ReactionFoodRepo reactionFoodRepo;
	@Autowired
	ReactionRepository reactionRepository;

	public final int MAX_SIZE_FOOD = 100;

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
		Food f = new Food();
		f.setName(foodRequest.getName());
		f.setPrice(foodRequest.getPrice());
		f.setContent(foodRequest.getContent());
		f.setProfile(profileRepository.findByAccountId(ime.getId()));
		f.setFiles(ConvertUtils.convertArrFileReqToSetFile(foodRequest.getFiles()));
		f.setCreateAt(DateUtils.currentTimestamp());
		f.setTag(tagRepository.findById(foodRequest.getTagId()).get());
		foodRepository.save(f);
	}

	@Override
	public FoodDetailResponse foodDetail(Long foodId) {
		Food f = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("tag not found"));
		if(f.getDisableAt()!=null) {
			return null;
		}
		FoodDetailResponse detailResponse = new FoodDetailResponse();
		detailResponse.setContent(f.getContent());
		detailResponse.setFiles(f.getFiles().stream().map((file) -> file.getPath()).collect(Collectors.toList()));
		detailResponse.setId(f.getId());
		detailResponse.setName(f.getName());
		detailResponse.setPrice(f.getPrice());
		detailResponse.setTag(new TagResponse(f.getTag().getId(), f.getTag().getTagName()));
		detailResponse.setTotalReaction(reactionFoodRepo.totalFoodReaction(foodId));
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
		swr.setName(p.getName());
		swr.setStoreId(p.getId());
		return swr;
	}

	@Override
	public PageResponse<StoreWallResponse> listStoreFollow(PageRequest pageRequest) {
		Account ime = itMe();
		Pageable pageable = PageUtils.toPageable(pageRequest);
		Page<AccountFollow> accountFollow = followRepository.listStoreFollow(ime.getUsername(), pageable);
		return new PageResponse<>(accountFollow.stream().map((al) -> {
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
	public PageResponse<SimpleAccountResponse> listFollowStore(PageRequest pageRequest) {
		Account ime = itMe();
		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		Pageable pageable = PageUtils.toPageable(pageRequest);
		Page<AccountFollow> accountFollow = followRepository.listFolowerStore(ime.getUsername(), pageable);
		return new PageResponse<>(accountFollow.stream().map((al) -> {
			SimpleAccountResponse swr = new SimpleAccountResponse();
			Profile p = profileRepository.findByAccountId(al.getAccount().getId());
			swr.setAddress(p.getAddress());
			swr.setAvatar(p.getAvatar());
			swr.setBio(p.getBio());
			swr.setCover(p.getCover());
			swr.setName(p.getName());
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
		food.setDisableAt(DateUtils.currentTimestamp());
		foodRepository.save(food);
	}

	@Override
	public PageResponse<FoodDetailResponse> listFood(PageRequest pageRequest) {
		Account ime = itMe();
		if (ime.getRole().compareTo(AccountRole.ROLE_STORE) != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		Pageable pageable = PageUtils.toPageable(pageRequest);
		Page<Food> foods = foodRepository.findByAccountUsername(ime.getUsername(), pageable);
		return new PageResponse<>(foods.stream()
				.map((food) -> new FoodDetailResponse(food.getId(), food.getName(), food.getPrice(), food.getContent(),
						food.getFiles().stream().map((file) -> file.getPath()).collect(Collectors.toList()),
						new TagResponse(food.getTag().getId(), food.getTag().getTagName()),reactionFoodRepo.totalFoodReaction(food.getId())))
				.collect(Collectors.toList()), foods.getTotalElements(), pageRequest.getPageSize());
	}

	@Override
	@Transactional
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
		food.setTag(tagRepository.findById(foodRequest.getTagId()).orElse(null));
		food = foodRepository.save(food);

		return new FoodDetailResponse(food.getId(), food.getName(), food.getPrice(), food.getContent(),
				food.getFiles().stream().map((file) -> file.getPath()).collect(Collectors.toList()),
				new TagResponse(food.getTag().getId(), food.getTag().getTagName()),reactionFoodRepo.totalFoodReaction(food.getId()));
	}

	@Override
	@Transactional
	public PostResponse createPost(PostRequest postRequest) {
		Account ime = itMe();
		if (postRequest.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
		}
		Post p = new Post();
		p.setContent(postRequest.getContent());
		p.setFiles(ConvertUtils.convertArrFileReqToSetFile(postRequest.getFiles()));
		p.setCreateAt(DateUtils.currentTimestamp());
		p.setProfile(profileRepository.findByAccountId(ime.getId()));
		String address = "";
		for(String addr:postRequest.getAddress()) {
			address+=addr+";";
		}
		p.setAddress(address);
		p.setFoods(postRequest.getFoodIds().stream().map((id) -> {
			return foodRepository.findById(id).get();
		}).collect(Collectors.toList()));
		
		if (postRequest.getTagIds() != null) {
			p.setTags(tagRepository.findAllListTagId(postRequest.getTagIds()));
		}
		p = postRepository.save(p);
		return new PostResponse(p.getId(), p.getContent(), ConvertUtils.convertSetToArrFile(p.getFiles()), p.getTags()
				.stream().map((tag) -> new TagResponse(tag.getId(), tag.getTagName())).collect(Collectors.toList()),
				p.getAddress() !=null ? p.getAddress().split(";"):null,
				p.getFoods().stream().map((f)->{
					return new FoodResponse(f.getId(), f.getName(), f.getPrice(), f.getContent(),ConvertUtils.convertSetToArrFile(f.getFiles()), f.getProfile().getName());
				}).collect(Collectors.toList()),ProfileResponse.builder().phoneNumber(p.getProfile().getAccount().getPhoneNumber()).build());
	}

	@Override
	@Transactional
	public void deletePost(Long postId) {
		Account ime = itMe();
		Post p = postRepository.findByAccountAndPostId(ime.getUsername(), postId).orElse(null);
		if (p == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.OTHER_ACTION_IS_DENIED);
		}
		postRepository.delete(p);
	}

	@Override
	public PostResponse detailPost(Long id) {
		Post p = postRepository.findById(id).orElse(null);
		if( p != null) {
		return new PostResponse(p.getId(), p.getContent(), ConvertUtils.convertSetToArrFile(p.getFiles()), p.getTags()
				.stream().map((tag) -> new TagResponse(tag.getId(), tag.getTagName())).collect(Collectors.toList()),
				p.getAddress() !=null ? p.getAddress().split(";"):null,
				p.getFoods().stream().map((f)->{
					return new FoodResponse(f.getId(), f.getName(), f.getPrice(), f.getContent(),ConvertUtils.convertSetToArrFile(f.getFiles()), f.getProfile().getName());
				}).collect(Collectors.toList()),ProfileResponse.builder().phoneNumber(p.getProfile().getAccount().getPhoneNumber()).build());
		}
		return null;
	}

	@Override
	public PageResponse<PostResponse> getListPost(Long accountId, PageRequest pageRequest) {
		Account itMe = itMe();
		Pageable pageable = PageUtils.toPageable(pageRequest);
		Page<Post> listPost = postRepository.findAllByAccount(accountId, pageable);
		List<PostResponse> listPostResponse = new ArrayList<PostResponse>();
		if (listPost == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.NULL_DATA);
		}
		for(Post p:listPost.getContent()) {
			listPostResponse.add(new PostResponse(p.getId(), p.getContent(), ConvertUtils.convertSetToArrFile(p.getFiles()),p.getTags()
					.stream().map((tag) -> new TagResponse(tag.getId(), tag.getTagName())).collect(Collectors.toList()),
					p.getAddress() !=null ? p.getAddress().split(";"):null,
					p.getFoods().stream().map((f)->{
						return new FoodResponse(f.getId(), f.getName(), f.getPrice(), f.getContent(),ConvertUtils.convertSetToArrFile(f.getFiles()), f.getProfile().getName());
					}).collect(Collectors.toList()),ProfileResponse.builder().phoneNumber(p.getProfile().getAccount().getPhoneNumber()).build()));
		}
		PageResponse<PostResponse> pageResponse = new PageResponse(listPostResponse, listPost.getTotalElements(),
				pageRequest.getPageSize());
		pageResponse.setStatus(true);
		pageResponse.setMessage(Message.OTHER_SUCCESS);
		return pageResponse;
	}

	@Override
	public PostResponse updatePost(PostRequest postRequest) {
		Post post = postRepository.findById(postRequest.getId()).get();
		if (post == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.POST_NOT_EXISTS);
		}
		post.setUpdateAt(DateUtils.currentTimestamp());
		if (postRequest.getFiles() != null) {
			post.setFiles(ConvertUtils.convertArrFileReqToSetFile(postRequest.getFiles()));
		}
		if (postRequest.getContent() != null) {
			post.setContent(postRequest.getContent());
		}
		if (postRequest.getTagIds() != null) {
			post.setTags(tagRepository.findAllListTagId(postRequest.getTagIds()));
		}
		String address = "";
		for(String addr:postRequest.getAddress()) {
			address+=addr+";";
		}
		post.setAddress(address);
		post.setFoods(postRequest.getFoodIds().stream().map((id) -> {
			return foodRepository.findById(id).get();
		}).collect(Collectors.toList()));
		post = postRepository.save(post);
		return new PostResponse(post.getId(), post.getContent(), ConvertUtils.convertSetToArrFile(post.getFiles()), post.getTags()
				.stream().map((tag) -> new TagResponse(tag.getId(), tag.getTagName())).collect(Collectors.toList()),
				post.getAddress() !=null ? post.getAddress().split(";"):null,
				post.getFoods().stream().map((f)->{
					return new FoodResponse(f.getId(), f.getName(), f.getPrice(), f.getContent(),ConvertUtils.convertSetToArrFile(f.getFiles()), f.getProfile().getName());
				}).collect(Collectors.toList()),ProfileResponse.builder().phoneNumber(post.getProfile().getAccount().getPhoneNumber()).build());
	}

	@Override
	public PageResponse<FoodDetailResponse> searchFoods(SearchFoodsRequest foodsRequest, PageRequest pageRequest) {
		Double min = foodsRequest.getMinPrice() == null ? 0 : foodsRequest.getMinPrice();
		Double max = foodsRequest.getMaxPrice() == null ? Double.MAX_VALUE : foodsRequest.getMaxPrice();
		List<Food> foods = storeCustomRepository.searchFoods(foodsRequest.getFoodName(), foodsRequest.getTagName(), min,
				max, pageRequest);
		List<FoodDetailResponse> response = foods.stream()
				.map((food) -> new FoodDetailResponse(food.getId(), food.getName(), food.getPrice(), food.getContent(),
						food.getFiles().stream().map((file) -> file.getPath()).collect(Collectors.toList()),
						new TagResponse(food.getTag().getId(), food.getTag().getTagName()),reactionFoodRepo.totalFoodReaction(food.getId())))
				.collect(Collectors.toList());
		return new PageResponse<>(response,
				storeCustomRepository.countFoodsSearch(foodsRequest.getFoodName(), foodsRequest.getTagName(), min, max),
				pageRequest.getPageSize());
	}

	@Override
	public PageResponse<FoodDetailResponse> searchFoodsSort(PageRequest pageRequest) {
		Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getPageSize(),
				Sort.by(Direction.valueOf(pageRequest.getOrder().toUpperCase()), pageRequest.getOrderBy()));
		Page<Food> foods = foodRepository.findAllAndSort(pageable);
		return new PageResponse<>(foods.stream()
				.map((food) -> new FoodDetailResponse(food.getId(), food.getName(), food.getPrice(), food.getContent(),
						food.getFiles().stream().map((file) -> file.getPath()).collect(Collectors.toList()),
						new TagResponse(food.getTag().getId(), food.getTag().getTagName()),reactionFoodRepo.totalFoodReaction(food.getId())))
				.collect(Collectors.toList()), foods.getTotalElements(), pageRequest.getPageSize());
	}
	
	@Transactional
	@Override
	public void foodReaction(FoodReactionRequest foodReactionRequest) {
		Reaction reaction = reactionFoodRepo.findReactionByFoodIdAndAccountId(foodReactionRequest.getFoodId()
				, itMe().getId());
		if(reaction!=null) {
			reaction.setDisableAt(null);
			reactionRepository.save(reaction);
			return;
		}
		reaction = new Reaction(ReactType.LOVE, accountRepository.findById(itMe().getId()).get());
		reaction = reactionRepository.save(reaction);
		ReactionFood reactionFood = new ReactionFood();
		reactionFood.setReaction(reaction);
		reactionFood.setFood(foodRepository.findById(foodReactionRequest.getFoodId()).get());
		reactionFoodRepo.save(reactionFood);
	}
	
	@Override
	public void foodReactionDel(FoodReactionRequest foodReactionRequest) {
		Reaction reaction = reactionFoodRepo.findReactionByFoodIdAndAccountId(foodReactionRequest.getFoodId()
				, itMe().getId());
		if(reaction==null) {
			return;
		}
		reaction.setDisableAt(DateUtils.currentTimestamp());
		
		reactionRepository.save(reaction);
	}
}