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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    @Autowired
    AttachmentRepository attachmentRepository;

    @Override
    @Transactional
    public MessageResponse<ConversationResponse> createConversation(CreateConversationRequest createConversationRequest) {
        MessageResponse<ConversationResponse> conversationMessageResponse = new MessageResponse<>();
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

        ConversationResponse conversationResponse = new ConversationResponse();
        conversationResponse.setCreateAt(conversation.getCreateAt());
        conversationResponse.setCreateBy(profileRepository.findByAccountId(itMe.getId()));
        conversationResponse.setTitle(conversation.getTitle());
        conversationResponse.setId(conversation.getId());
        conversationMessageResponse.setStatus(true);
        conversationMessageResponse.setMessage(Message.OTHER_SUCCESS);
        conversationMessageResponse.setData(conversationResponse);
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
        MessageResponse<MessagesResponse> messagesResponseMessagesResponse = new MessageResponse<>();
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
        Messages finalMessages = messages;
        sendMessageRequest.getAttachments().forEach(attachment -> {
            Attachments attachments = new Attachments();
            attachments.setMessages(finalMessages);
            attachments.setCreateAt(presentTimestamp);
            attachments.setFileUrl(attachment);
            attachmentRepository.save(attachments);
        });
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
            messagesResponse.setAttachments(attachmentRepository.findByMessagesId(messages.getId()));
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
        Messages messages = messagesRepository.getMessageFromAccount(itMe.getId(), messageRemoveRequest.getIdMessage());
        if (messages == null) {
            messageRemoveResponseMessageResponse.setMessage(Message.MESSAGE_NOT_EXISTS);
            return messageRemoveResponseMessageResponse;
        }
        messages.setDisableAt(DateUtils.currentTimestamp());
        messagesRepository.save(messages);
        MessageRemoveResponse messageRemoveResponse = new MessageRemoveResponse();
        messageRemoveResponse.setIdMessage(messageRemoveRequest.getIdMessage());
        messageRemoveResponseMessageResponse.setStatus(true);
        messageRemoveResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        messageRemoveResponseMessageResponse.setData(messageRemoveResponse);
        return messageRemoveResponseMessageResponse;
    }

    @Override
    @Transactional
    public MessageResponse<MessageDeleteConversationResponse> deleteConversation(MessageDeleteConversationRequest messageDeleteConversationRequest) {
        MessageResponse<MessageDeleteConversationResponse> messageDeleteConversationResponseMessageResponse = new MessageResponse<>();
        messageDeleteConversationResponseMessageResponse.setType(MessageType.DELETE_CONVERSATION);
        Account itMe = accountRepository.findById(messageDeleteConversationRequest.getAccountId()).orElse(null);
        Participants participants = participantsRepository.getParticipantsFromAccount(itMe.getId(), messageDeleteConversationRequest.getConversationId());
        if (participants == null) {
            messageDeleteConversationResponseMessageResponse.setMessage(Message.MESSAGE_CONVERSATION_NOT_EXISTS);
            return messageDeleteConversationResponseMessageResponse;
        }
        Timestamp presentTimestamp = DateUtils.currentTimestamp();
        participants.setDisableAt(presentTimestamp);
        participantsRepository.save(participants);
        MessageDeleteConversationResponse messageDeleteConversationResponse = new MessageDeleteConversationResponse();
        messageDeleteConversationResponse.setConversationId(messageDeleteConversationRequest.getConversationId());
        messageDeleteConversationResponseMessageResponse.setStatus(true);
        messageDeleteConversationResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        messageDeleteConversationResponseMessageResponse.setData(messageDeleteConversationResponse);
        return messageDeleteConversationResponseMessageResponse;
    }

    @Override
    @Transactional
    public MessageResponse<MessageHeartResponse> reactHeart(MessageHeartRequest messageHeartRequest) {
        MessageResponse<MessageHeartResponse> messageHeartResponseMessageResponse = new MessageResponse<>();
        messageHeartResponseMessageResponse.setType(MessageType.REACT_MESSAGE);
        Account itMe = accountRepository.findById(messageHeartRequest.getAccountId()).orElse(null);
        Messages messages = messagesRepository.findById(messageHeartRequest.getIdMessage()).orElse(null);
        if (messages == null || messages.getDisableAt() != null) {
            messageHeartResponseMessageResponse.setMessage(Message.MESSAGE_NOT_EXISTS);
            return messageHeartResponseMessageResponse;
        }
        Conversation conversation = conversationRepsitory.findByIdFromAccountId(itMe.getId(), messages.getConversation().getId());
        if (conversation == null) {
            messageHeartResponseMessageResponse.setMessage(Message.MESSAGE_NOT_EXISTS);
            return messageHeartResponseMessageResponse;
        }
        messages.setHeart(messages.getHeart() + 1);
        messages = messagesRepository.save(messages);
        //
        MessageHeartResponse messageHeartResponse = new MessageHeartResponse();
        messageHeartResponse.setIdMessage(messages.getId());
        messageHeartResponse.setTotalHeart((long) messages.getHeart());
        messageHeartResponseMessageResponse.setStatus(true);
        messageHeartResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        messageHeartResponseMessageResponse.setData(messageHeartResponse);
        return messageHeartResponseMessageResponse;
    }

    @Override
    @Transactional
    public MessageResponse<MessageAddResponse> addMember(MessageAddRequest messageAddRequest) {
        MessageResponse<MessageAddResponse> messageAddResponseMessageResponse = new MessageResponse<>();
        messageAddResponseMessageResponse.setType(MessageType.ADD_MEMBER);
        Timestamp presentTimestamp = DateUtils.currentTimestamp();
        Account itMe = accountRepository.findById(messageAddRequest.getAccountId()).orElse(null);
        Account youAdd = accountRepository.findById(messageAddRequest.getAccountAdd()).orElse(null);
        if (youAdd == null) {
            messageAddResponseMessageResponse.setMessage(Message.AUTH_LOGIN_USERNAME_WRONG);
            return messageAddResponseMessageResponse;
        }
        Conversation conversation = conversationRepsitory.findByIdAndCreateBy(messageAddRequest.getConversationId(), itMe);
        if (conversation == null) {
            messageAddResponseMessageResponse.setMessage(Message.MESSAGE_CONVERSATION_NOT_FOUND_OR_NOT_ADMIN);
            return messageAddResponseMessageResponse;
        }
        Participants participants = participantsRepository.findByConversationAndAccount(conversation, youAdd);
        if (participants == null) {
            participants = new Participants();
            participants.setAccount(youAdd);
            participants.setCreateAt(presentTimestamp);
            participants.setConversation(conversation);
        }
        participants.setUpdateAt(presentTimestamp);
        participants.setDisableAt(null);
        participants = participantsRepository.save(participants);
        MessageAddResponse messageAddResponse = new MessageAddResponse();
        messageAddResponse.setAccountAdd(messageAddRequest.getAccountAdd());
        messageAddResponse.setAccountId(messageAddRequest.getAccountId());
        messageAddResponse.setConversationId(messageAddRequest.getConversationId());
        messageAddResponse.setProfile(profileRepository.findFiendProfileByAccountId(participants.getAccount().getId()).orElse(null));
        messageAddResponseMessageResponse.setStatus(true);
        messageAddResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        messageAddResponseMessageResponse.setData(messageAddResponse);
        return messageAddResponseMessageResponse;
    }

    @Override
    @Transactional
    public MessageResponse<MessageKickResponse> deleteMember(MessageKickRequest messageKickRequest) {
        MessageResponse<MessageKickResponse> messageKickResponseMessageResponse = new MessageResponse<>();
        messageKickResponseMessageResponse.setType(MessageType.KICK_MEMBER);
        Account itMe = accountRepository.findById(messageKickRequest.getAccountId()).orElse(null);
        Account youDelete = accountRepository.findById(messageKickRequest.getAccountIdKick()).orElse(null);
        if (youDelete == null) {
            messageKickResponseMessageResponse.setMessage(Message.AUTH_LOGIN_USERNAME_WRONG);
            return messageKickResponseMessageResponse;
        }
        Conversation conversation = conversationRepsitory.findByIdAndCreateBy(messageKickRequest.getConversationId(), itMe);
        if (conversation == null) {
            messageKickResponseMessageResponse.setMessage(Message.MESSAGE_CONVERSATION_NOT_FOUND_OR_NOT_ADMIN);
            return messageKickResponseMessageResponse;
        }
        Participants participants = participantsRepository.findByConversationAndAccount(conversation, youDelete);
        if (participants == null) {
            messageKickResponseMessageResponse.setMessage(Message.MESSAGE_CONVERSATION_MEMBER_NOT_EXISTS);
            return messageKickResponseMessageResponse;
        }
        participants.setDisableAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        MessageKickResponse messageKickResponse = new MessageKickResponse();
        messageKickResponse.setAccountIdKick(messageKickRequest.getAccountIdKick());
        messageKickResponse.setAccountId(messageKickRequest.getAccountId());
        messageKickResponse.setConversationId(messageKickResponse.getConversationId());
        messageKickResponseMessageResponse.setData(messageKickResponse);
        messageKickResponseMessageResponse.setStatus(true);
        messageKickResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        return messageKickResponseMessageResponse;
    }

    @Override
    public MessageResponse<MessageInfoResponse> infoConversation(MessageInfoRequest messageInfoRequest) {
        MessageResponse<MessageInfoResponse> messageInfoResponseMessageResponse = new MessageResponse<>();
        messageInfoResponseMessageResponse.setType(MessageType.INFO_CONVERSATION);
        Conversation conversation = conversationRepsitory.findByIdFromAccountId(messageInfoRequest.getAccountId(), messageInfoRequest.getConversationId());
        if (conversation == null) {
            messageInfoResponseMessageResponse.setMessage(Message.MESSAGE_CONVERSATION_NOT_FOUND);
            return messageInfoResponseMessageResponse;
        }
        MessageInfoResponse messageInfoResponse = new MessageInfoResponse();
        List<ProfileResponse> profileResponseList = new ArrayList<>();
        List<Participants> participantsList = participantsRepository.getAllByConversationId(conversation.getId());
        participantsList.forEach(participants -> {
            profileResponseList.add(profileRepository.findFiendProfileByAccountId(participants.getAccount().getId()).orElse(null));
        });
        messageInfoResponse.setMembers(profileResponseList);
        ConversationResponse conversationResponse = new ConversationResponse();
        conversationResponse.setId(conversation.getId());
        conversationResponse.setTitle(conversation.getTitle());
        conversationResponse.setUpdateAt(conversation.getUpdateAt());
        conversationResponse.setCreateAt(conversation.getCreateAt());
        conversationResponse.setCreateBy(profileRepository.findByAccountId(conversation.getCreateBy().getId()));
        messageInfoResponse.setConversation(conversationResponse);
        messageInfoResponse.setAccountId(messageInfoRequest.getAccountId());
        messageInfoResponse.setConversationId(messageInfoRequest.getConversationId());
        messageInfoResponseMessageResponse.setData(messageInfoResponse);
        messageInfoResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        messageInfoResponseMessageResponse.setStatus(true);
        return messageInfoResponseMessageResponse;
    }

    @Override
    public MessageResponse<MessageUpdateConversationResponse> updateConversation(MessageUpdateConversationRequest messageUpdateConversationRequest) {
        MessageResponse<MessageUpdateConversationResponse> messageUpdateConversationResponseMessageResponse = new MessageResponse<>();
        messageUpdateConversationResponseMessageResponse.setType(MessageType.UPDATE_CONVERSATION);
        Account itMe = accountRepository.findById(messageUpdateConversationRequest.getAccountId()).orElse(null);
        Conversation conversation = conversationRepsitory.findByIdAndCreateBy(messageUpdateConversationRequest.getConversationId(), itMe);
        if (conversation == null) {
            messageUpdateConversationResponseMessageResponse.setMessage(Message.MESSAGE_CONVERSATION_NOT_FOUND_OR_NOT_ADMIN);
            return messageUpdateConversationResponseMessageResponse;
        }
        conversation.setTitle(messageUpdateConversationRequest.getTitle());
        conversation.setImage(messageUpdateConversationRequest.getImage());
        conversation.setUpdateAt(DateUtils.currentTimestamp());
        conversation = conversationRepsitory.save(conversation);
        MessageUpdateConversationResponse messageUpdateConversationResponse = new MessageUpdateConversationResponse();
        messageUpdateConversationResponse.setAccountId(messageUpdateConversationRequest.getAccountId());
        messageUpdateConversationResponse.setConversationId(messageUpdateConversationRequest.getConversationId());
        messageUpdateConversationResponse.setImage(conversation.getImage());
        messageUpdateConversationResponse.setTitle(conversation.getTitle());
        messageUpdateConversationResponseMessageResponse.setStatus(true);
        messageUpdateConversationResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        messageUpdateConversationResponseMessageResponse.setData(messageUpdateConversationResponse);
        return messageUpdateConversationResponseMessageResponse;
    }

    @Override
    public MessageResponse<MessageRoomTransferResponse> roomTransfer(MessageRoomTransferRequest messageRoomTransferRequest) {
        MessageResponse<MessageRoomTransferResponse> messageRoomTransferResponseMessageResponse = new MessageResponse<>();
        messageRoomTransferResponseMessageResponse.setType(MessageType.TRANSFER_CONVERSATION);
        Account itMe = accountRepository.findById(messageRoomTransferRequest.getAccountId()).orElse(null);
        Conversation conversation = conversationRepsitory.findByIdAndCreateBy(messageRoomTransferRequest.getConversationId(), itMe);
        if (conversation == null) {
            messageRoomTransferResponseMessageResponse.setMessage(Message.MESSAGE_CONVERSATION_NOT_FOUND_OR_NOT_ADMIN);
            return messageRoomTransferResponseMessageResponse;
        }
        Account account = accountRepository.findById(messageRoomTransferRequest.getAccountIdCheckIn()).orElse(null);
        Participants participants = participantsRepository.findByConversationAndAccount(conversation, account);
        if (participants == null) {
            messageRoomTransferResponseMessageResponse.setMessage(Message.MESSAGE_CONVERSATION_MEMBER_NOT_EXISTS);
            return messageRoomTransferResponseMessageResponse;
        }
        conversation.setCreateBy(account);
        conversation.setUpdateAt(DateUtils.currentTimestamp());
        conversationRepsitory.save(conversation);
        ProfileResponse profileResponse = profileRepository.findFiendProfileByAccountId(account.getId()).orElse(null);
        MessageRoomTransferResponse messageRoomTransferResponse = new MessageRoomTransferResponse();
        messageRoomTransferResponse.setAccountId(messageRoomTransferRequest.getAccountId());
        messageRoomTransferResponse.setConversationId(messageRoomTransferRequest.getConversationId());
        messageRoomTransferResponse.setProfileCheckIn(profileResponse);
        messageRoomTransferResponseMessageResponse.setStatus(true);
        messageRoomTransferResponseMessageResponse.setMessage(Message.OTHER_SUCCESS);
        messageRoomTransferResponseMessageResponse.setData(messageRoomTransferResponse);
        return messageRoomTransferResponseMessageResponse;
    }
}
