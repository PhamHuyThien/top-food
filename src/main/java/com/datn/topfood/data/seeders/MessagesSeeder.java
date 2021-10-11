package com.datn.topfood.data.seeders;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Conversation;
import com.datn.topfood.data.model.Messages;
import com.datn.topfood.data.model.Participants;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.ConversationRepsitory;
import com.datn.topfood.data.repository.jpa.MessagesRepository;
import com.datn.topfood.data.repository.jpa.ParticipantsRepository;
import com.datn.topfood.util.BeanUtils;
import com.datn.topfood.util.DateUtils;

public class MessagesSeeder implements Seeder {

    @Override
    public void seed() {
        AccountRepository accountRepository = BeanUtils.getBean(AccountRepository.class);
        ConversationRepsitory conversationRepsitory = BeanUtils.getBean(ConversationRepsitory.class);
        ParticipantsRepository participantsRepository = BeanUtils.getBean(ParticipantsRepository.class);
        MessagesRepository messagesRepository = BeanUtils.getBean(MessagesRepository.class);
        //
        Account thien = accountRepository.findByUsername("thien");
        Account minh = accountRepository.findByUsername("minh");
        Conversation conversation = new Conversation();
        conversation.setId(1L);
        conversation.setTitle("Thiên, Minh");
        conversation.setCreateBy(thien);
        conversation.setCreateAt(DateUtils.currentTimestamp());
        conversationRepsitory.save(conversation);
        Participants participants = new Participants();
        participants.setId(1L);
        participants.setAccount(thien);
        participants.setConversation(conversation);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        participants.setId(2L);
        participants.setAccount(minh);
        participants.setConversation(conversation);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        Messages messages = new Messages();
        messages.setId(1L);
        messages.setMessage("Chào minh");
        messages.setCreateAt(DateUtils.currentTimestamp());
        messages.setConversation(conversation);
        messages.setAccount(thien);
        messagesRepository.save(messages);
        messages.setId(2L);
        messages.setMessage("Chào thiên");
        messages.setAccount(minh);
        messagesRepository.save(messages);
        //
        Account vuong = accountRepository.findByUsername("vuong");
        Account khai = accountRepository.findByUsername("khai");
        conversation = new Conversation();
        conversation.setId(2L);
        conversation.setTitle("Nhóm BACKEND.");
        conversation.setCreateBy(thien);
        conversation.setCreateAt(DateUtils.currentTimestamp());
        conversationRepsitory.save(conversation);
        participants = new Participants();
        participants.setId(3L);
        participants.setAccount(thien);
        participants.setConversation(conversation);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        participants = new Participants();
        participants.setId(4L);
        participants.setAccount(vuong);
        participants.setConversation(conversation);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        participants = new Participants();
        participants.setId(5L);
        participants.setAccount(khai);
        participants.setConversation(conversation);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        messages.setId(3L);
        messages.setMessage("Hế lô mọi người");
        messages.setConversation(conversation);
        messages.setAccount(thien);
        messages = messagesRepository.save(messages);
        messages.setId(3L);
        messages.setMessages(messages);
        messages.setId(4L);
        messages.setMessage("Hế lô thiên");
        messages.setConversation(conversation);
        messages.setAccount(khai);
        messagesRepository.save(messages);
        messages.setMessages(null);
        messages.setId(5L);
        messages.setAccount(vuong);
        messages.setMessage("Mình là vương đây.");
        messagesRepository.save(messages);
        //
        Account nga = accountRepository.findByUsername("nga");
        Account thang = accountRepository.findByUsername("thang");
        conversation = new Conversation();
        conversation.setId(3L);
        conversation.setTitle("Nhóm FRONTEND.");
        conversation.setCreateBy(minh);
        conversation.setCreateAt(DateUtils.currentTimestamp());
        conversationRepsitory.save(conversation);
        participants.setId(6L);
        participants.setAccount(minh);
        participants.setConversation(conversation);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        participants = new Participants();
        participants.setId(7L);
        participants.setAccount(nga);
        participants.setConversation(conversation);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        participants = new Participants();
        participants.setId(8L);
        participants.setAccount(thang);
        participants.setConversation(conversation);
        participants.setCreateAt(DateUtils.currentTimestamp());
        participantsRepository.save(participants);
        messages.setId(6L);
        messages.setMessage("Hế lô mọi người");
        messages.setConversation(conversation);
        messages.setAccount(thang);
        messagesRepository.save(messages);
    }
}
