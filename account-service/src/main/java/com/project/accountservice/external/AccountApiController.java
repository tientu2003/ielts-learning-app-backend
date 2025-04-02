package com.project.accountservice.external;

import com.project.accountservice.AccountService;
import com.project.accountservice.internal.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountApiController {

    private final AccountService accountService;

    @PostMapping("/new")
    public ResponseEntity<String> newAccount(@RequestBody Account account) {
        try{
            String id = accountService.createAccountInfor(account);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Account> getAccounts() {
        Account account = accountService.findById();
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/band")
    public ResponseEntity<Account> updateAccount(@RequestParam("type") TargetType type, @RequestParam("target") String newBand) {
        return ResponseEntity.ok(accountService.updateAccountTheTargetBand(type, newBand));
    }


    @PatchMapping("/bod")
    public ResponseEntity<Account> updateAccountDateOfBirth(@RequestParam("date") Date dateOfBirth) {
        return ResponseEntity.ok(accountService.updateDateOfBirth(dateOfBirth));
    }

    @PatchMapping("/time-target")
    public ResponseEntity<Account> updateAccountTimeTarget(@RequestParam("date") Date dateOfTarget) {
        return ResponseEntity.ok(accountService.updateTimeTarget(dateOfTarget));

    }

}
