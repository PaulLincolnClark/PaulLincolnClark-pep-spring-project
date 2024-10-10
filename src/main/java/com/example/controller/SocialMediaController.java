package com.example.controller;
import com.example.entity.*;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController { 

    AccountService accountService;
    MessageService messageService;
    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }

    //REGISTER A NEW USER
    @PostMapping(value = "/register")
    public ResponseEntity<Account> persistAccount(@RequestBody Account account){
        Account attempt = accountService.persistAccount(account);
        if (attempt == null) {
            return ResponseEntity.status(409).body(attempt);
        }
        else if(attempt.getPassword().length() < 4) {
            return ResponseEntity.status(400).body(attempt);
        }
        else {
            return ResponseEntity.status(200).body(attempt);
        }
    }

    //LOGIN TO AN EXISTING ACCOUNT
    @PostMapping(value = "/login")
    public ResponseEntity<Account> login(@RequestBody Account account){     
        Account attempt = accountService.login(account);
        if (attempt == null) {
            return ResponseEntity.status(401).body(attempt);
        }
        else { 
            return ResponseEntity.status(200).body(attempt);
        }
    }

    //CREATE A NEW MESSAGE
    @PostMapping(value = "/messages")
    public ResponseEntity<Message> persistMessage(@RequestBody Message message) {
        Message attempt = messageService.persistMessage(message);
        if (attempt == null) {
            return ResponseEntity.status(400).body(attempt);
        }
        else { 
            return ResponseEntity.status(200).body(attempt);
        }
    }

    //GET ALL MESSAGES
    @GetMapping(value = "/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> attempt = messageService.getAllMessages();
        return ResponseEntity.status(200).body(attempt);
    }

    //GET MESSAGE BY MESSAGE ID
    @GetMapping(value = "/messages/{messageId}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable Integer messageId) {
        Message attempt = messageService.getMessageByMessageId(messageId);
        return ResponseEntity.status(200).body(attempt);
    }

    //DELETE MESSAGE BY MESSAGE ID
    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable Integer messageId) {
        Integer attempt = messageService.deleteMessageByMessageId(messageId);
        return ResponseEntity.status(200).body(attempt);
    }

    //PATCH MESSAGE BY MESSAGE ID
    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageByMessageId(@PathVariable Integer messageId, @RequestBody String messageText) {
        Integer attempt = messageService.updateMessageByMessageId(messageId, messageText);
        System.out.println("\n" + attempt + "\n");
        if (attempt == null) {
            return ResponseEntity.status(400).body(attempt);
        }
        else {
            return ResponseEntity.status(200).body(attempt);
        }
    }

    //GET ALL MESSAGES BY ACCOUNT ID
    @GetMapping(value = "/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByPostedBy(@PathVariable Integer accountId) {
        List<Message> attempt = messageService.getAllMessagesByPostedBy(accountId);
        return ResponseEntity.status(200).body(attempt);
    }
}
