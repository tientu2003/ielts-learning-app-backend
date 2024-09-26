package com.project.accountservice;

import com.project.accountservice.external.AccountDataRequest;
import com.project.accountservice.internal.Account;
import com.project.accountservice.internal.SameOldPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class RestApiAccountService {

    final AccountService accountService;

    @Autowired
    public RestApiAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    ResponseEntity<Account> findAccountByUserId(@RequestParam(value = "userId",required = true) UUID userId) {
        Account account = accountService.findById(userId);
        if(account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{username}")
    ResponseEntity<Account> findAccountByUserName(@PathVariable String username) {
        Account account = accountService.findAccountByUserName(username);
        if(account != null) {
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<Account> createAccount(@RequestBody AccountDataRequest account) {
        Account received = accountService.createAccount(account.getUserName(), account.getPassword());
        if(received == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{username}")
                .buildAndExpand(received.getUserName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{username}/{password}")
    ResponseEntity<Account> updateAccount(@PathVariable String username, @PathVariable String password) {
        Account account = accountService.findAccountByUserName(username);
        if(account != null) {
            try{
                Account newPassword = accountService.changePassword(account, password);
                return ResponseEntity.ok(newPassword);
            }catch (SameOldPasswordException e){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.noContent().build();
    }
}

