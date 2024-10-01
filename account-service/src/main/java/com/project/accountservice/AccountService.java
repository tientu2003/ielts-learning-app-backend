package com.project.accountservice;

import com.project.accountservice.internal.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public interface AccountService {

    Account findById(UUID id);

    Account findAccountByUserName(String userName);

    @Transactional
    Account createAccount(String userName, String password);

    @Transactional
    Account changePassword(Account account,String newPassword);

    boolean checkAuthentication(String userName, String password);
}
