package com.project.accountservice.internal;

import com.project.accountservice.AccountService;
import com.project.accountservice.external.TargetType;
import com.project.accountservice.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        return switch (type){
            case AVERAGE -> accountRepository.updateTargetBandById(newBand, userId);
            case READ -> accountRepository.updateReadingBandById(newBand, userId);
            case LISTEN -> accountRepository.updateListeningBandById(newBand, userId);
            case WRITE -> accountRepository.updateWritingBandById(newBand, userId);
            case SPEAK -> accountRepository.updateSpeakingBandById(newBand, userId);
        };
    }

    @Override
    public Account updateDateOfBirth(Date dateOfBirth) {
        return accountRepository.updateDateOfBirthById(dateOfBirth, userService.getUserId());
    }

    @Override
    public Account updateTimeTarget(Date dateOfTarget) {
        return accountRepository.updateDateTargetById(dateOfTarget, userService.getUserId());
    }
}
