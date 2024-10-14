package com.example.service;
import com.example.repository.*;
import com.example.entity.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account registerAccount(Account account){
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return accountRepository.save(account);

    }
    public Account login(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));


    }



}
