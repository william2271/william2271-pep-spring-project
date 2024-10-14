package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.*;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    @Transactional
    public Message createMessage(Message message)throws IllegalArgumentException{
        String messageText = message.getMessageText();
        if (messageText == null || messageText.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (messageText.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Integer postedBy = message.getPostedBy();

        if (postedBy == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Account> accountOptional = accountRepository.findById(postedBy);
        if (accountOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Message messageSave = new Message();
        messageSave.setMessageText(message.getMessageText());
        messageSave.setPostedBy(postedBy);
        messageSave.setTimePostedEpoch(message.getTimePostedEpoch() );

        return messageRepository.save(messageSave);

    }

    public Message getMessageById(Integer messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);


        return messageOptional.orElse(null);
    }
    @Transactional
    public int deleteMessageById(Integer messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }
    @Transactional
    public int updateMessageText(Integer messageId, String newMessage){
        
        if(newMessage == null || newMessage.trim().isEmpty() ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
        if(newMessage.length() >255){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            Message message = messageOptional.get();
            message.setMessageText(newMessage);
            messageRepository.save(message);
            return 1;
        }else {
            return 0;
        }
    }
    public List<Message> getMessagesByAccountId(Integer postedBy){
        return messageRepository.findByPostedBy(postedBy);
    }




}
