package com.project.accountservice.internal;

import com.project.accountservice.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImp implements AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountServiceImp(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountByUserName(String userName) {
        return accountRepository.findByUserName(userName);
    }

    @Override
    public Account findById(UUID userId){
        return accountRepository.findById(userId).orElse(null);
    }

    @Override
    public Account createAccount(String userName, String password) {

        if(accountRepository.existsByUserName(userName)) {
            return null;
        }

        Account account = Account.builder()
                .id(UUID.randomUUID())
                .userName(userName)
                .password(new BCryptPasswordEncoder().encode(password))
                .build();

        return accountRepository.save(account);
    }

    @Override
    public Account changePassword(Account account, String newPassword) {

        if(new BCryptPasswordEncoder().matches(newPassword, account.getPassword())) {
            throw new SameOldPasswordException();
        }

        String encodedPassword = new BCryptPasswordEncoder().encode(newPassword);

        account.setPassword(encodedPassword);

        return accountRepository.save(account);
    }

    @Override
    public boolean checkAuthentication(String userName, String password) {
        Account account = findAccountByUserName(userName);
        if(account == null) {
            return false;
        }
        return new BCryptPasswordEncoder().matches(password, account.getPassword());
    }
}
