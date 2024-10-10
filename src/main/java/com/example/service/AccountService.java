package com.example.service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
 
    public Account persistAccount(Account account) {
        if(accountRepository.findByUsername(account.getUsername()).isEmpty() == false) {
            return null;
        }
        if(account.getPassword().length() < 4) {
            return account;
        }
        else {
            accountRepository.save(account);
            return account;
        }
    }
 
    public Account login (Account account) {
        if(accountRepository.findByUsername(account.getUsername()).isEmpty() == false) {
            Account storedAccount = accountRepository.findByUsername(account.getUsername()).get(0);
            if (account.getPassword().equals(storedAccount.getPassword())) {
                account.setAccountId(storedAccount.getAccountId());
                return account;
            }
        }
        return null;
    }
}
