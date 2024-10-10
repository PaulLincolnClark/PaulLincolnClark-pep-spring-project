package com.example.service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.Message;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    } 

    public Message persistMessage(Message message) {
        try {
            String messageText = message.getMessageText();
            accountRepository.getById(message.getPostedBy());            
            if (messageText.isBlank() == false && messageText.length() <= 255) {
                messageRepository.save(message);
                return message;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
        
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(Integer messageId) {
        List<Message> messages = getAllMessages();
        for (Message message : messages) {
            if(message.getMessageId().equals(messageId)) {
                return message;
            }
        }
        return null;
    }

    public Integer deleteMessageByMessageId(Integer messageId) {
        try {
            messageRepository.deleteById(messageId);
            return 1;
        }
        catch (Exception e) {
            return null;
        }
    }

    public Integer updateMessageByMessageId(Integer messageId, String messageText) {
        String messageTrimmed = messageText.substring(17, messageText.length() - 2);
        System.out.println("\n" + messageTrimmed + "\n");
        try {
            Message message = getMessageByMessageId(messageId);
            if (messageTrimmed.isBlank() == false && messageTrimmed.length() <= 255) {
                message.setMessageText(messageText);
                return 1;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public List<Message> getAllMessagesByPostedBy(Integer postedBy) {
        List<Message> messages = messageRepository.findByPostedBy(postedBy);
        return messages;
    }
}
