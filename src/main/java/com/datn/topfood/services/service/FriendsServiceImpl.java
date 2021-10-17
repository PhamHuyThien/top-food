package com.datn.topfood.services.service;

import java.util.ArrayList;
import java.util.List;

import com.datn.topfood.dto.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.FriendShip;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.custom.impl.FriendshipCustomRepository;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FriendShipRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.ProfileResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.FriendsService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.FriendShipStatus;

@Service
public class FriendsServiceImpl extends BaseService implements FriendsService {
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
    public void sendFriendInvitations(SendFriendInvitationsRequest friendInvitationsRequest) {
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
    }

    @Override
    @Transactional
    public void blockFriend(BlockFriendRequest blockFriendRequest) {
        Account itMe = itMe();
        Account blockPerson = accountRepository.findByPhoneNumber(blockFriendRequest.getPhoneNumberBlockPerson());
        // số điện thoại không tồn tại
        if (blockPerson == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.ACCOUNT_FRIEND_BY_PHONE_NOT_FOUND);
        }

        // không thể tự block chính mình
        if (blockFriendRequest.getPhoneNumberBlockPerson().equals(itMe.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
        }

        FriendShip friendShip = friendShipRepository.findFriendShipRelation(itMe.getUsername(),
                blockPerson.getUsername());
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
        friendShip.setBlockBy(itMe);
        friendShip.setStatus(FriendShipStatus.BLOCK);
        friendShip.setUpdateAt(DateUtils.currentTimestamp());
        friendShipRepository.save(friendShip);
    }

    @Override
    @Transactional
    public void replyFriend(ReplyInvitationFriendRequest replyInvitationFriendRequest) {
        Account itMe = itMe();
        FriendShip friendShip = friendShipRepository.findFriendByReplyPersonToRequestPerson(itMe.getUsername(),
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
    }

    @Override
    public PageResponse<ProfileResponse> getListFriends(PageRequest pageRequest) {
        Account itMe = itMe();
        pageRequest = PageUtils.ofDefault(pageRequest);
        List<ProfileResponse> friendProfileResponseList = friendshipCustomRepository
                .findByListFriends(itMe.getId(), pageRequest);
        PageResponse pageResponse = new PageResponse<>(friendProfileResponseList,
                friendshipCustomRepository.getTotalProfile(itMe.getId()), pageRequest.getPageSize());
        pageResponse.setStatus(true);
        pageResponse.setMessage(Message.OTHER_SUCCESS);
        return pageResponse;
    }

    @Override
    public PageResponse<ProfileResponse> getListFriendsRequest(PageRequest pageRequest) {
        Account itMe = itMe();
        Pageable pageable = PageUtils.toPageable(pageRequest);
        Page<FriendShip> friendShipList = friendShipRepository.findByAccountAddresseeAndStatus(itMe.getId(), pageable);
        List<ProfileResponse> friendProfileResponseList = new ArrayList<>();
        friendShipList.forEach((friendShip) -> {
            Account accountRequest = friendShip.getAccountRequest();
            Profile profile = profileRepository.findByAccountId(accountRequest.getId());
            friendProfileResponseList.add(new ProfileResponse(accountRequest.getId(),
                    accountRequest.getUsername(),
                    accountRequest.getPhoneNumber(),
                    accountRequest.getEmail(),
                    accountRequest.getRole(),
                    profile)
            );
        });
        PageResponse pageResponse = new PageResponse(friendProfileResponseList, friendShipList.getTotalElements(),
                pageRequest.getPageSize());
        pageResponse.setStatus(true);
        pageResponse.setMessage(Message.OTHER_SUCCESS);
        return pageResponse;
    }

    @Override
    @Transactional
    public void removeFriend(String removeFriendRequest) {
        Account itMe = itMe();
        Account fiendRemove = accountRepository.findByPhoneNumber(removeFriendRequest);
        // số điện thoại không tồn tại
        if (fiendRemove == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.ACCOUNT_FRIEND_BY_PHONE_NOT_FOUND);
        }

        // không thể tự hủy kết bạn với chính mình chính mình
        if (removeFriendRequest.equals(itMe.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
        }
        FriendShip friendShip = friendShipRepository.findFriendShipRelation(itMe.getUsername(),
                fiendRemove.getUsername());
        // hai người chưa có mối quan hệ trong database
        if (friendShip == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
        } else {
            // quan hệ đang là block thì không xóa
            if (friendShip.getStatus().compareTo(FriendShipStatus.BLOCK) == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
            }
            // nếu đang là sending thì sẽ là hủy lời mời kết bạn, nếu là friend thì sẽ hủy
            // kết bạn
            friendShipRepository.delete(friendShip);
        }

    }

    @Override
    public PageResponse<ProfileResponse> getListFriendBlock(PageRequest pageRequest) {
        Account itMe = itMe();
        Pageable pageable = PageUtils.toPageable(pageRequest);
        Page<FriendShip> friendShipPage = friendShipRepository.getAllByAccountAddOrAccountReqAndBlockAt(itMe.getId(), itMe.getId(), itMe.getId(), pageable);
        List<ProfileResponse> profileResponseList = new ArrayList<>();
        friendShipPage.toList().forEach(friendShip -> {
            ProfileResponse profileResponse = profileRepository.findFiendProfileByAccountId(friendShip.getId()).orElse(null);
            profileResponseList.add(profileResponse);
        });
        PageResponse<ProfileResponse> profileResponsePageResponse = new PageResponse<>(
                profileResponseList,
                friendShipPage.getTotalElements(),
                pageable.getPageSize()
        );
        profileResponsePageResponse.setStatus(true);
        profileResponsePageResponse.setMessage(Message.OTHER_SUCCESS);
        return profileResponsePageResponse;
    }

    @Override
    public void unblockFriend(UnblockFriendRequest unblockFriendRequest) {
        Account itMe = itMe();
        PageRequest pageRequest = new PageRequest();
        Pageable pageable = PageUtils.toPageable(pageRequest);
        Page<FriendShip> friendShipPage = friendShipRepository.getAllByAccountAddOrAccountReqAndBlockAt(
                unblockFriendRequest.getAccountIdUnblock(),
                unblockFriendRequest.getAccountIdUnblock(),
                itMe.getId(),
                pageable
        );
        if(friendShipPage.getTotalElements() < 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.FRIENDS_ACCOUNT_NOT_IN_BLOCK_LIST);
        }
        FriendShip friendShip = friendShipPage.toList().get(0);
        friendShipRepository.delete(friendShip);
    }

}
