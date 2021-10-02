package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.repository.custom.impl.FriendshipCustomRepository;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void sendFriendInvitations(SendFriendInvitationsRequest friendInvitationsRequest) {
        Account itMe = itMe();
        Account friend = accountRepository.findByPhoneNumber(friendInvitationsRequest.getPhoneAddressee());
        if (friend == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.ACCOUNT_FRIEND_BY_PHONE_NOT_FOUND);
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
        FriendShip friendShip = friendShipRepository.findFriendShipRelation(
                blockFriendRequest.getUsernameRequestPerson(), blockFriendRequest.getUsernameBlockPerson());
        // nếu chưa là bạn bè sẽ từ chối block
        if (friendShip == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.OTHER_ACTION_IS_DENIED);
        }
        friendShip.setDeleteAt(DateUtils.currentTimestamp());
        friendShip.setStatus(FriendShipStatus.BLOCK);
        friendShipRepository.save(friendShip);
    }

    @Override
    @Transactional
    public void replyFriend(ReplyInvitationFriendRequest replyInvitationFriendRequest) {
        FriendShip friendShip = friendShipRepository.findFriendByReplyPersonToRequestPerson(
                replyInvitationFriendRequest.getUsernameReplyPerson(),
                replyInvitationFriendRequest.getUsernameRequestPerson());

        if (friendShip == null) {
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
}
