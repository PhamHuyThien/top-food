package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.custom.impl.FriendshipCustomRepository;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.datn.topfood.data.model.FriendShip;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FriendShipRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.BlockFriendRequest;
import com.datn.topfood.dto.request.ReplyInvitationFriendRequest;
import com.datn.topfood.dto.request.SendFriendInvitationsRequest;
import com.datn.topfood.dto.response.FriendProfileResponse;
import com.datn.topfood.services.interf.AccountService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.FriendShipStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl extends BaseService implements AccountService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FriendShipRepository friendShipRepository;
    @Autowired
    FriendshipCustomRepository friendshipCustomRepository;

    @Override
    @Transactional
    public FriendProfileResponse getFiendProfileByAccountId(Long id) {
        return profileRepository.findFiendProfileByAccountId(id);
    }

    @Override
    @Transactional
    public Boolean sendFriendInvitations(SendFriendInvitationsRequest friendInvitationsRequest) {
        Account itMe = itMe();
        Account friend = accountRepository.findByPhoneNumber(friendInvitationsRequest.getPhoneAddressee());
        // số điện thoại không tồn tại
        if (friend == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.ACCOUNT_FRIEND_BY_PHONE_NOT_FOUND);
        }
        // mối quan hệ đã tồn tại không thể gửi lời mời tiếp
        if (friendShipRepository.findFriendShipRelation(itMe.getUsername(), friend.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.ACCOUNT_FRIEND_SHIP_EXIST);
        }
        // không thể tự kết bạn với chính mình
        if (friendInvitationsRequest.getPhoneAddressee().equals(itMe.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
        }
        FriendShip friendShip = new FriendShip();
        friendShip.setStatus(FriendShipStatus.SENDING);
        friendShip.setAccountRequest(itMe);
        friendShip.setAccountAddressee(friend);
        friendShip.setCreateAt(DateUtils.currentTimestamp());
        friendShipRepository.save(friendShip);
        return true;
    }

    @Override
    @Transactional
    public Boolean blockFriend(BlockFriendRequest blockFriendRequest) {
        Account itMe = itMe();
        Account blockPerson = accountRepository.findByPhoneNumber(blockFriendRequest.getPhoneNumberBlockPerson());
        // số điện thoại không tồn tại
        if (blockPerson == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.ACCOUNT_FRIEND_BY_PHONE_NOT_FOUND);
        }
        FriendShip friendShip = friendShipRepository.findFriendShipRelation(itMe.getUsername(),
                blockPerson.getUsername());
        // không thể tự block chính mình
        if (blockFriendRequest.getPhoneNumberBlockPerson().equals(itMe.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
        }
        // nếu chưa có mối quan hệ trong database thì tạo mối quan hệ mới
        if (friendShip == null) {
            friendShip = new FriendShip();
            friendShip.setAccountRequest(itMe);
            friendShip.setAccountAddressee(blockPerson);
            friendShip.setCreateAt(DateUtils.currentTimestamp());
        } else {
            // không thể block người mà mình đã block
            if (friendShip.getStatus().compareTo(FriendShipStatus.BLOCK) == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
            }
        }

        friendShip.setStatus(FriendShipStatus.BLOCK);
        friendShipRepository.save(friendShip);
        return true;
    }

    @Override
    @Transactional
    public Boolean replyFriend(ReplyInvitationFriendRequest replyInvitationFriendRequest) {
        Account itMe = itMe();
        FriendShip friendShip = friendShipRepository.findFriendByReplyPersonToRequestPerson(
                itMe.getUsername(),
                replyInvitationFriendRequest.getUsernameSendInvitaionPerson());
        // lời mời kết bạn không tồn tại
        if (friendShip == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.ACCOUNT_FRIEND_INVITATION_NOT_EXIST);
        }

        // hai người đã là bạn bè
        if (friendShip.getStatus().compareTo(FriendShipStatus.FRIEND) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
        }

        if (replyInvitationFriendRequest.getStatusReply()) {
            friendShip.setUpdateAt(DateUtils.currentTimestamp());
            friendShip.setStatus(FriendShipStatus.FRIEND);
            friendShipRepository.save(friendShip);
        } else {
            // không chấp nhận kết bạn sẽ xóa luôn quan hệ tạm thời (SENDING)
            friendShipRepository.delete(friendShip);
        }
        return true;
    }

    @Override
    @Transactional
    public PageResponse<FriendProfileResponse> getListFriends(PageRequest pageRequest) {
        Account itMe = itMe();
        pageRequest = PageUtils.ofDefault(pageRequest);
        List<FriendProfileResponse> friendProfileResponseList = friendshipCustomRepository.findByListFriends(itMe.getId(), pageRequest);
        PageResponse pageResponse = new PageResponse<>(
                friendProfileResponseList,
                friendshipCustomRepository.getTotalProfile(itMe.getId()),
                pageRequest.getPageSize()
        );
        pageResponse.setStatus(true);
        pageResponse.setMessage(Message.OTHER_SUCCESS);
        return pageResponse;
    }

    @Override
    public PageResponse<FriendProfileResponse> getListFriendsRequest(PageRequest pageRequest) {
        Account itMe = itMe();
        Pageable pageable = PageUtils.toPageable(pageRequest);
        Page<FriendShip> friendShipList = friendShipRepository.findByAccountAddresseeAndStatus(itMe.getId(), pageable);
        List<FriendProfileResponse> friendProfileResponseList = new ArrayList<>();
        friendShipList.forEach((friendShip) -> {
            Account accountRequest = friendShip.getAccountRequest();
            Profile profile = profileRepository.findByAccountId(accountRequest.getId());
            friendProfileResponseList.add(new FriendProfileResponse(
                    accountRequest.getId(),
                    accountRequest.getPhoneNumber(),
                    accountRequest.getEmail(),
                    profile
            ));
        });
        PageResponse pageResponse = new PageResponse(
                friendProfileResponseList,
                friendShipList.getTotalElements(),
                pageRequest.getPageSize()
        );
        pageResponse.setStatus(true);
        pageResponse.setMessage(Message.OTHER_SUCCESS);
        return pageResponse;
    }
}