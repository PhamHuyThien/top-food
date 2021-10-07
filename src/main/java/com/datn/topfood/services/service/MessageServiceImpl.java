package com.datn.topfood.services.service;

import com.datn.topfood.data.model.*;
import com.datn.topfood.data.repository.jpa.*;
import com.datn.topfood.dto.request.CreateConversationRequest;
import com.datn.topfood.dto.request.SendMessageRequest;
import com.datn.topfood.services.BaseService;
import com.datn.topfood.services.interf.MessageService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.constant.Message;
import com.datn.topfood.util.enums.ParticipantsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        List<Profile> profileList = getProfileList(createConversationRequest.getProfileId());
        Timestamp presentTimestamp = DateUtils.currentTimestamp();
        Conversation conversation = new Conversation();
        conversation.setTitle(buildNameConversation(createConversationRequest.getName(), profileList));
        conversation.setCreateAt(presentTimestamp);
        conversation.setCreateBy(itMe);
        conversation = conversationRepsitory.save(conversation);
        for (Profile profile : profileList) {
            Participants participants = new Participants();
            participants.setConversation(conversation);
            participants.setCreateAt(presentTimestamp);
            participants.setAccount(profile.getAccount());
            participants.setStatus(ParticipantsStatus.JOIN);
            participantsRepository.save(participants);
        }
        conversation.setCreateBy(null);
        return conversation;
    }

    private String buildNameConversation(String name, List<Profile> profileList) {
        if (name == null) {
            for (Profile profile : profileList) {
                name += profile.getName() + ", ";
            }
        }
        return name;
    }

    private List<Profile> getProfileList(List<Long> profileId) {
        List<Profile> profileList = new ArrayList<>();
        for (Long aLong : profileId) {
            Profile profile = profileRepository.findById(aLong).orElse(null);
            if (profile != null) {
                profileList.add(profile);
            }
        }
        return profileList;
    }

    @Override
    public void sendMessage(SendMessageRequest sendMessageRequest) {
        Account itMe = itMe();
        Conversation conversation = conversationRepsitory.findById(sendMessageRequest.getIdConversation()).orElse(null);
        Messages quoteMessage = null;
        if (conversation == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.MESSAGE_CONVERSATION_NOT_FOUND);
        }
        if (sendMessageRequest.getQuoteMessageId() != null) {
            quoteMessage = messagesRepository.findById(sendMessageRequest.getQuoteMessageId()).orElse(null);
        }
        Messages messages = new Messages();
        messages.setMessage(sendMessageRequest.getMessage());
        messages.setAccount(itMe);
        messages.setConversation(conversation);
        messages.setCreateAt(DateUtils.currentTimestamp());
        messages.setMessages(quoteMessage);
        messagesRepository.save(messages);
    }
}
