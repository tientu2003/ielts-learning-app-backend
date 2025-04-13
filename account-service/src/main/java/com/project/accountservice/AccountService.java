package com.project.accountservice;

import com.project.accountservice.external.TargetType;
import com.project.accountservice.internal.Account;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface AccountService {

    Account findById();

    String createAccountInfor(Account account);

    Account updateAccountTheTargetBand(TargetType type, String newBand);

    Account updateDateOfBirth(Date dateOfBirth);

    Account updateTimeTarget(Date dateOfTarget);
}
