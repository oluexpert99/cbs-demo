package com.cbs.democbs.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.cbs.democbs.common.PostgresDatabaseContainerInitializer;
import com.cbs.democbs.domain.data.CreateSavingsAccountRequest;
import com.cbs.democbs.domain.data.CustomPageResponse;
import com.cbs.democbs.domain.data.SavingsAccountData;
import com.cbs.democbs.domain.model.SavingsAccount;
import com.cbs.democbs.domain.persistence.SavingsAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;



@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {PostgresDatabaseContainerInitializer.class})
 class SavingsAccountServiceTest {

    @Autowired
    private SavingsAccountService savingsAccountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @BeforeEach
    void setUp() {
        createSavingsAccount();
    }



    @Test
    @Order(value = 1)
    void testConnectionToDatabase() {
        Assertions.assertNotNull(savingsAccountRepository);
//        Assertions.assertNotNull(userInfoRepository);
    }

    @Test
    @Order(value = 2)
    void testGetAccounts() {
             CustomPageResponse<SavingsAccountData> response = savingsAccountService.getAccounts(PageRequest.of(0, 10));
        assertEquals(1, response.getTotalElements());
    }


    @Test
    void testCreateAccount() {
        CreateSavingsAccountRequest request = new CreateSavingsAccountRequest();
        request.setAccountName("John Doe");
        request.setMinRequiredBalance(BigDecimal.ZERO);
        SavingsAccountData accountData = savingsAccountService.createAccount(request);

        // Add assertions based on your expectations
        assertNotNull(accountData);
    }
    @Test
    void testGetAccountByAccountNumber() {
        String accountNumber = "1234567890";
        SavingsAccount account = savingsAccountService.getAccountByAccountNumber(accountNumber);

        // Add assertions based on your expectations
        assertNotNull(account);
    }

    // Add similar tests for other methods in the interface



    @Test
    void testUpdateAccount() {
        Long savingsAccountId = 1L;
        CreateSavingsAccountRequest request = new CreateSavingsAccountRequest();
        request.setAccountName("John Doe Jnr");
        savingsAccountService.updateAccount(savingsAccountId, request);

        // Add assertions based on your expectations
        // assert that the new account name and min required balance are updated
        var updatedAccount = savingsAccountService.getAccountData(savingsAccountId);
        assertEquals("John Doe Jnr", updatedAccount.getAccountName());
    }

    private void createSavingsAccount() {
        if (savingsAccountRepository.count() > 0) {
            return;
        }
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountNumber("1234567890");
        savingsAccount.setAccountName("John Doe");
        savingsAccount.setMinRequiredBalance(BigDecimal.ZERO);
        savingsAccountRepository.save(savingsAccount);
    }
}
