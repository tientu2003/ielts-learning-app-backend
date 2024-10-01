package com.project.accountservice;

import com.project.accountservice.internal.Account;
import com.project.accountservice.internal.SameOldPasswordException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan("com.project.accountservice")
@EnableTransactionManagement
public class AccountServiceTests {

    @Autowired
    AccountService accountService;



    @Test
    void shouldCreateAccount() {
        Account receivedAccount = accountService
                .createAccount("userTest1", "abcde");

        assertNotNull(receivedAccount);

        assertThat(receivedAccount.getId()).isInstanceOf(UUID.class);

        assertThat(receivedAccount.getUserName()).isEqualTo("userTest1");

        assertTrue(new BCryptPasswordEncoder().matches("abcde", receivedAccount.getPassword()));
    }

    @Test
    void shouldNotCreateAccountIfUserNameUsed(){
        Account receivedAccount = accountService
                .createAccount("ilibero0", "TestPassword");

        assertNull(receivedAccount);
    }

    @Test
    void findAccountByUsername() {
        Account receivedAccount =  accountService.findAccountByUserName("ilibero0");

        assertNotNull(receivedAccount);

        assertThat("39848b38-5e09-49c9-904f-3b0e9967c85c").isEqualTo(receivedAccount.getId().toString());
    }

    @Test
    void shouldNotReturnIfAccountNotFound() {
        Account receivedAccount = accountService.findAccountByUserName("NOTFOUND");

        assertNull(receivedAccount);
    }

    @Test
    void shouldSuccessAuthWhenProvideValidUserNameAndPasswordOfSameOwner() {
        accountService.createAccount("userTest1", "abcde");
        accountService.createAccount("userTest2", "fghik");

        boolean result = accountService.checkAuthentication("userTest1",
                "abcde");

        assertTrue(result);

        boolean result1 = accountService.checkAuthentication("userTest1",
                "fghik");

        assertFalse(result1);
    }

    @Test
    void shouldNotAuthenticatedWhenProvideInValidUserNameOrPassword() {
        boolean result1 = accountService.checkAuthentication("WRONGUSERNAME",
                "abcde");

        assertFalse(result1);

        boolean result2 = accountService.checkAuthentication("ilibero0",
                "WRONGPASSWORD");

        assertFalse(result2);
    }


    @Test
    void shouldChangePassword() {

        Account receivedAccount = accountService
                .createAccount("userTest1", "abcde");

        Account account = accountService.findAccountByUserName("userTest1");

        assertThrows(SameOldPasswordException.class, () ->
        {
            accountService.changePassword(account
                    , "abcde");
        });

        Account received1 = accountService.changePassword(account, "NewTestPassword");

        assertNotNull(received1);

        assertTrue(new BCryptPasswordEncoder().matches("NewTestPassword", received1.getPassword()));

    }

}

