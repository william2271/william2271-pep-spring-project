package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.example.entity.*;
import com.example.service.*;

import java.util.List;
import java.util.Map;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final MessageService messageService;
    private final AccountService accountService;


     @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService= accountService;
    }
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List <Message> messages = messageService.getAllMessages();
       return ResponseEntity.ok(messages) ;

    }
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") Integer messageId){
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable("messageId") Integer messageId){
        int deleted = messageService.deleteMessageById(messageId);
        if(deleted == 1){
            return ResponseEntity.ok(deleted);
        } else {
            return ResponseEntity.ok().build();
        }

        
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable("messageId") Integer messageId, @RequestBody Map<String, String> requestBody){

        String newMessage = requestBody.get("messageText");
        int update = messageService.updateMessageText(messageId,newMessage);

        if(update==1){
            return ResponseEntity.ok(update);
        } else if(update ==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account){
       
            Account newAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(newAccount);
        
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody Account login){
        Account account = accountService.login(login.getUsername(), login.getPassword());
        return ResponseEntity.ok(account);

    
    }
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessageByAccountId(@PathVariable("account_id") Integer postedBy){
        List<Message> messages = messageService.getMessagesByAccountId(postedBy);
        return ResponseEntity.ok(messages);
    }





}
