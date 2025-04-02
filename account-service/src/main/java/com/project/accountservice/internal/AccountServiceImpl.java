package com.project.accountservice.internal;

import com.project.accountservice.AccountService;
import com.project.accountservice.external.TargetType;
import com.project.accountservice.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

    private final UserService userService;

    private final AccountRepository accountRepository;

    @Override
    public Account findById() {
        return accountRepository.findById(userService.getUserId()).orElse(null);
    }

    @Override
    public String createAccountInfor(Account account) {
        account.setId(userService.getUserId());
        Account actual = accountRepository.save(account);
        return actual.getId();
    }

    @Override
    public Account updateAccountTheTargetBand(TargetType type, String newBand) {
        String userId = userService.getUserId();
        switch (type){
            case AVERAGE -> accountRepository.updateTargetBandById(newBand, userId);
            case READ -> accountRepository.updateReadingBandById(newBand, userId);
            case LISTEN -> accountRepository.updateListeningBandById(newBand, userId);
            case WRITE -> accountRepository.updateWritingBandById(newBand, userId);
            case SPEAK -> accountRepository.updateSpeakingBandById(newBand, userId);
        }
        return accountRepository.findById(userId).orElse(null);
    }

    @Override
    public Account updateDateOfBirth(Date dateOfBirth) {
        Optional<Account> optionalAccount = accountRepository.findById(userService.getUserId());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setDateOfBirth(dateOfBirth);
            return accountRepository.save(account);
        } else {
            return null;
        }
    }

    @Override
    public Account updateTimeTarget(Date dateOfTarget) {
        Optional<Account> optionalAccount = accountRepository.findById(userService.getUserId());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setTimeTarget(dateOfTarget);
            return accountRepository.save(account);
        } else {
            return null;
        }
    }
}
