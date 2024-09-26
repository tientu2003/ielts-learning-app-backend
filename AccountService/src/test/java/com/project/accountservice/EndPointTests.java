package com.project.accountservice;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.project.accountservice.external.AccountDataRequest;
import com.project.accountservice.internal.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndPointTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldFindCorrectAccount() {

        //BY USERNAME
        ResponseEntity<Account> accountResponseEntity = restTemplate.getForEntity("/accounts/ilibero0", Account.class);
        assertEquals(HttpStatus.OK,accountResponseEntity.getStatusCode());

        Account account = accountResponseEntity.getBody();
        assertNotNull(account);

        assertEquals("39848b38-5e09-49c9-904f-3b0e9967c85c",account.getId().toString());

        //NOT FOUND
        ResponseEntity<Account> accountResponseEntity1 = restTemplate.getForEntity("/accounts/ABCDE", Account.class);
        assertEquals(accountResponseEntity1.getStatusCode(), HttpStatus.NOT_FOUND);

        // BY ID
        ResponseEntity<Account> accountResponseEntity2
                = restTemplate.getForEntity("/accounts?userId=39848b38-5e09-49c9-904f-3b0e9967c85c", Account.class);
        assertEquals(accountResponseEntity2.getStatusCode(), HttpStatus.OK);

        ResponseEntity<Account> accountResponseEntity3 = restTemplate.getForEntity("/accounts?userId=ABCDE ", Account.class);
        assertEquals(accountResponseEntity3.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldCreateNewAccount() {

        AccountDataRequest newAccount = new AccountDataRequest();

        newAccount.setUserName("TestCreateUser1");
        newAccount.setPassword("PasswordTest");

        URI response = restTemplate.postForLocation("/accounts", newAccount, Account.class);

        assertNotNull(response);

        ResponseEntity<Account> account =restTemplate.getForEntity(response, Account.class);

        assertEquals(HttpStatus.OK,account.getStatusCode());

        Account account1 = account.getBody();

        assertNotNull(account1);

        assertEquals("TestCreateUser1",account1.getUserName());
    }


    @Test
    void shouldUpdateExistingAccount() {
        ResponseEntity<Account> response = restTemplate.exchange("/accounts/ilibero0/1234",HttpMethod.PUT,null,Account.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());

        Account account1 = response.getBody();

        assertNotNull(account1);

        assertEquals("ilibero0",account1.getUserName());

        assertTrue(new BCryptPasswordEncoder().matches("1234",account1.getPassword()));

    }


}
