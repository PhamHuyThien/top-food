package com.datn.topfood.services.service;

import com.datn.topfood.data.model.*;
import com.datn.topfood.data.repository.jpa.*;
import com.datn.topfood.dto.request.CreateConversationRequest;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.SendMessageRequest;
import com.datn.topfood.dto.response.AccountProfileResponse;
import com.datn.topfood.dto.response.ConversationResponse;
import com.datn.topfood.dto.response.MessagesResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.MessageService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.ParticipantsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl extends BaseService implements MessageService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ConversationRepsitory conversationRepsitory;
    @Autowired
    ParticipantsRepository participantsRepository;
    @Autowired
    MessagesRepository messagesRepository;

    @Override
    @Transactional
    public Conversation createConversation(CreateConversationRequest createConversationRequest) {
        Account itMe = itMe();
        AccountProfileResponse accountProfileMe = getAccountProfile(itMe.getId());
        AccountProfileResponse accountProfileRec = getAccountProfile(createConversationRequest.getAccountId());
        Timestamp presentTimestamp = DateUtils.currentTimestamp();
        Conversation conversation = new Conversation();
        conversation.setTitle(buildNameConversation(
                createConversationRequest.getName(),
                accountProfileMe.getProfile().getName(),
                accountProfileRec.getProfile().getName())
        );
        conversation.setCreateAt(presentTimestamp);
        conversation.setUpdateAt(presentTimestamp);
        conversation.setCreateBy(itMe);
        conversation = conversationRepsitory.save(conversation);
        saveParticipants(conversation, presentTimestamp, accountProfileMe.getAccount());
        saveParticipants(conversation, presentTimestamp, accountProfileRec.getAccount());
        itMe.setPassword(null);
        conversation.setCreateBy(itMe);
        return conversation;
    }

    private void saveParticipants(Conversation conversation, Timestamp presentTimestamp, Account account) {
        Participants participants = new Participants();
        participants.setConversation(conversation);
        participants.setCreateAt(presentTimestamp);
        participants.setAccount(account);
        participantsRepository.save(participants);
    }

    private String buildNameConversation(String name, String nameA, String nameB) {
        if (name == null) {
            return nameA + ", " + nameB;
        }
        return name;
    }

    private AccountProfileResponse getAccountProfile(Long accountId) {
        return accountRepository.getAccountProfileFrivate(accountId);
    }

    @Override
    @Transactional
    public void sendMessage(SendMessageRequest sendMessageRequest) {
        Account itMe = itMe();
        Timestamp presentTimestamp = DateUtils.currentTimestamp();
        Conversation conversation = conversationRepsitory.findByIdFromAccountId(itMe.getId(), sendMessageRequest.getConversationId());
        if (conversation == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.MESSAGE_CONVERSATION_NOT_FOUND);
        }
        Messages quoteMessage = null;
        if (sendMessageRequest.getQuoteMessageId() != null) {
            quoteMessage = messagesRepository.findById(sendMessageRequest.getQuoteMessageId()).orElse(null);
        }
        Messages messages = new Messages();
        messages.setMessage(sendMessageRequest.getMessage());
        messages.setAccount(itMe);
        messages.setConversation(conversation);
        messages.setCreateAt(presentTimestamp);
        messages.setMessages(quoteMessage);
        messagesRepository.save(messages);
        conversation.setUpdateAt(presentTimestamp);
        conversationRepsitory.save(conversation);
    }

    @Override
    public PageResponse<ConversationResponse> getListConversation(PageRequest pageRequest) {
        Account itMe = itMe();
        Pageable pageable = PageUtils.toPageable(pageRequest);
        Page<ConversationResponse> conversationResponsePage = conversationRepsitory.getAllConversation(itMe.getId(), pageable);
        PageResponse<ConversationResponse> conversationResponsePageResponse = new PageResponse<>(
                conversationResponsePage.toList(),
                conversationResponsePage.getTotalElements(),
                pageable.getPageSize()
        );
        conversationResponsePageResponse.setStatus(true);
        conversationResponsePageResponse.setMessage(Message.OTHER_SUCCESS);
        return conversationResponsePageResponse;
    }

    @Override
    public PageResponse<MessagesResponse> getListMessages(Long conversationId, PageRequest pageRequest) {
        Account itMe = itMe();
        Pageable pageable = PageUtils.toPageable(pageRequest);
//        Page<MessagesResponse> messagesResponsePage = messagesRepository.getListMessages(itMe.getId(), conversationId, pageable);
        Page<Messages> messagesPage = messagesRepository.getListMessages(itMe.getId(), conversationId, pageable);
        List<MessagesResponse> messagesResponseList = new ArrayList<>();
        for (Messages messages : messagesPage) {
            MessagesResponse messagesResponse = toMessagesResponse(messages);
            messagesResponseList.add(messagesResponse);
        }
        PageResponse<MessagesResponse> messagesResponsePageResponse = new PageResponse<>(
                messagesResponseList,
                messagesPage.getTotalElements(),
                pageable.getPageSize()
        );
        messagesResponsePageResponse.setStatus(true);
        messagesResponsePageResponse.setMessage(Message.OTHER_SUCCESS);
        return messagesResponsePageResponse;
    }

    private MessagesResponse toMessagesResponse(Messages messages) {
        MessagesResponse messagesResponse = new MessagesResponse();
        messagesResponse.setId(messages.getId());
        messagesResponse.setMessage(messages.getMessage());
        messagesResponse.setHeart(messages.getHeart());
        messagesResponse.setCreateAt(messages.getCreateAt());
        messagesResponse.setUpdateAt(messages.getUpdateAt());
        messagesResponse.setCreateBy(profileRepository.findByAccountId(messages.getAccount().getId()));
        if (messages.getMessages() != null) {
            messagesResponse.setQuoteMessage(toMessagesResponse(messages.getMessages()));
        }
        return messagesResponse;
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId) {
        Account itMe = itMe();
        Messages messages = messagesRepository.getMessageFromAccount(itMe.getId(), messageId);
        if (messages == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.MESSAGE_NOT_EXISTS);
        }
        messages.setDisableAt(DateUtils.currentTimestamp());
        messagesRepository.save(messages);
    }

    @Override
    @Transactional
    public void deleteConversation(Long conversationId) {
        Account itMe = itMe();
        Participants participants = participantsRepository.getParticipantsFromAccount(itMe.getId(), conversationId);
        if (participants == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.MESSAGE_CONVERSATION_NOT_EXISTS);
        }
        Timestamp presentTimestamp = DateUtils.currentTimestamp();
        participants.setDisableAt(presentTimestamp);
        participantsRepository.save(participants);
    }

    @Override
    @Transactional
    public void reactHeart(Long messageId) {
        Account itMe = itMe();
        Messages messages = messagesRepository.findById(messageId).orElse(null);
        if (messages == null || messages.getDisableAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.MESSAGE_NOT_EXISTS);
        }
        Conversation conversation = conversationRepsitory.findByIdFromAccountId(itMe.getId(), messages.getConversation().getId());
        if (conversation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.MESSAGE_NOT_EXISTS);
        }
        messages.setHeart(messages.getHeart() + 1);
        messagesRepository.save(messages);
    }
}
