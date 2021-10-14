package com.datn.topfood.services.service;

import com.datn.topfood.data.model.*;
import com.datn.topfood.data.repository.jpa.*;
import com.datn.topfood.dto.messages.*;
import com.datn.topfood.dto.request.*;
import com.datn.topfood.dto.response.*;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.MessageService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.MessageType;
import com.datn.topfood.util.enums.ParticipantsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
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
    public MessageResponse<Conversation> createConversation(CreateConversationRequest createConversationRequest) {
        MessageResponse<Conversation> conversationMessageResponse = new MessageResponse<>();
        conversationMessageResponse.setType(MessageType.CREATE_CONVERSATION);
        Account itMe = accountRepository.findById(createConversationRequest.getAccountId()).orElse(null);
        AccountProfileResponse accountProfileMe = getAccountProfile(itMe.getId());
        AccountProfileResponse accountProfileRec = getAccountProfile(createConversationRequest.getAccountIdAdd());
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
        conversationMessageResponse.setStatus(true);
        conversationMessageResponse.setMessage(Message.OTHER_SUCCESS);
        conversationMessageResponse.setData(conversation);
        return conversationMessageResponse;
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
    public MessageResponse<MessagesResponse> sendMessage(SendMessageRequest sendMessageRequest) {
        MessageResponse<MessagesResponse> messagesResponseMessagesResponse = new MessageResponse();
        messagesResponseMessagesResponse.setType(MessageType.SEND);
        Account itMe = accountRepository.findById(sendMessageRequest.getAccountId()).orElse(null);
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
        messages = messagesRepository.save(messages);
        conversation.setUpdateAt(presentTimestamp);
        conversationRepsitory.save(conversation);

        MessagesResponse messagesResponse = toMessagesResponse(messages);
        messagesResponseMessagesResponse.setData(messagesResponse);
        messagesResponseMessagesResponse.setStatus(true);
        messagesResponseMessagesResponse.setMessage(Message.OTHER_SUCCESS);
        return messagesResponseMessagesResponse;
    }

    @Override
    public PageMessageResponse<ConversationResponse> getListConversation(PageMessageRequest pageMessageRequest) {
        Account itMe = accountRepository.findById(pageMessageRequest.getAccountId()).orElse(null);
        Pageable pageable = PageUtils.toPageable(pageMessageRequest);
        Page<ConversationResponse> conversationResponsePage = conversationRepsitory.getAllConversation(itMe.getId(), pageable);
        PageMessageResponse<ConversationResponse> conversationResponsePageMessageResponse = new PageMessageResponse<>(
                conversationResponsePage.toList(),
                conversationResponsePage.getTotalElements(),
                pageable.getPageSize(),
                MessageType.LIST_CONVERSATION
        );
        conversationResponsePageMessageResponse.setStatus(true);
        conversationResponsePageMessageResponse.setMessage(Message.OTHER_SUCCESS);
        return conversationResponsePageMessageResponse;
    }

    @Override
    public PageMessageResponse<MessagesResponse> getListMessages(PageMessageRequest pageMessageRequest) {
        Account itMe = accountRepository.findById(pageMessageRequest.getAccountId()).orElse(null);
        Pageable pageable = PageUtils.toPageable(pageMessageRequest);
//        Page<MessagesResponse> messagesResponsePage = messagesRepository.getListMessages(itMe.getId(), conversationId, pageable);
        Page<Messages> messagesPage = messagesRepository.getListMessages(itMe.getId(), pageMessageRequest.getConversationId(), pageable);
        List<MessagesResponse> messagesResponseList = new ArrayList<>();
        for (Messages messages : messagesPage) {
            MessagesResponse messagesResponse = toMessagesResponse(messages);
            messagesResponseList.add(messagesResponse);
        }
        PageMessageResponse<MessagesResponse> messagesResponsePageMessageResponse = new PageMessageResponse<>(
                messagesResponseList,
                messagesPage.getTotalElements(),
                pageable.getPageSize(),
                MessageType.LIST_MESSAGE
        );
        messagesResponsePageMessageResponse.setStatus(true);
        messagesResponsePageMessageResponse.setMessage(Message.OTHER_SUCCESS);
        return messagesResponsePageMessageResponse;
    }

    private MessagesResponse toMessagesResponse(Messages messages) {
        MessagesResponse messagesResponse = new MessagesResponse();
        try {
            messagesResponse.setId(messages.getId());
            messagesResponse.setMessage(messages.getMessage());
            messagesResponse.setHeart(messages.getHeart());
            messagesResponse.setCreateAt(messages.getCreateAt());
            messagesResponse.setUpdateAt(messages.getUpdateAt());
            messagesResponse.setCreateBy(profileRepository.findByAccountId(messages.getAccount().getId()));
            if (messages.getMessages() != null) {
                messagesResponse.setQuoteMessage(toMessagesResponse(messages.getMessages()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messagesResponse;
    }

    @Override
    @Transactional
    public MessageResponse<MessageRemoveResponse> deleteMessage(MessageRemoveRequest messageRemoveRequest) {
        MessageResponse<MessageRemoveResponse> messageRemoveResponseMessageResponse = new MessageResponse<>();
        messageRemoveResponseMessageResponse.setType(MessageType.REMOVE_MESSAGE);
        Account itMe = accountRepository.findById(messageRemoveRequest.getAccountId()).orElse(null);
        Messages messages = messagesRepository.getMessageFromAccount(itMe.getId(), messageRemoveRequest.getIdMessageRemove());
        if (messages == null) {
            messageRemoveResponseMessageResponse.setMessage(Message.MESSAGE_NOT_EXISTS);
            return messageRemoveResponseMessageResponse;
        }
        messages.setDisableAt(DateUtils.currentTimestamp());
        messagesRepository.save(messages);
        MessageRemoveResponse messageRemoveResponse = new MessageRemoveResponse();
        messageRemoveResponse.setIdMessageRemove(messageRemoveRequest.getIdMessageRemove());
        messageRemoveResponseMessageResponse.setStatus(true);
        messageRemoveResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        messageRemoveResponseMessageResponse.setData(messageRemoveResponse);
        return messageRemoveResponseMessageResponse;
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

    @Override
    @Transactional
    public void addMember(AddMemeberRequest addMemeberRequest) {
        Account youAdd = accountRepository.findById(addMemeberRequest.getAccountId()).orElse(null);
        if (youAdd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_LOGIN_USERNAME_WRONG);
        }
        Conversation conversation = conversationRepsitory.findByIdAndCreateBy(addMemeberRequest.getConversationId(), itMe());
        if (conversation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.MESSAGE_CONVERSATION_NOT_FOUND_OR_NOT_ADMIN);
        }
        Participants participants = participantsRepository.findByConversationAndAccount(conversation, youAdd);
        if (participants != null && participants.getDisableAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.MESSAGE_CONVERSATION_MEMBER_IS_EXISTS);
        }
        if (participants == null) {
            participants = new Participants();
        }
        participants.setConversation(conversation);
        participants.setAccount(youAdd);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participants.setDisableAt(null);
        participantsRepository.save(participants);
    }

    @Override
    @Transactional
    public void deleteMember(DeleteMemeberRequest deleteMemeberRequest) {
        Account youAdd = accountRepository.findById(deleteMemeberRequest.getAccountId()).orElse(null);
        if (youAdd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.AUTH_LOGIN_USERNAME_WRONG);
        }
        Conversation conversation = conversationRepsitory.findByIdAndCreateBy(deleteMemeberRequest.getConversationId(), itMe());
        if (conversation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.MESSAGE_CONVERSATION_NOT_FOUND_OR_NOT_ADMIN);
        }
        Participants participants = participantsRepository.findByConversationAndAccount(conversation, youAdd);
        if (participants == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.MESSAGE_CONVERSATION_MEMBER_NOT_EXISTS);
        }
        participants.setDisableAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
    }
}
