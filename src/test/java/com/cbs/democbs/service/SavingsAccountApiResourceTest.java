package com.cbs.democbs.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cbs.democbs.common.PostgresDatabaseContainerInitializer;
import com.cbs.democbs.domain.data.CreateSavingsAccountRequest;
import com.cbs.democbs.domain.data.CustomPageResponse;
import com.cbs.democbs.domain.data.SavingsAccountData;
import com.cbs.democbs.domain.model.SavingsAccount;
import com.cbs.democbs.domain.persistence.SavingsAccountRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {PostgresDatabaseContainerInitializer.class})
 class SavingsAccountApiResourceTest {



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
    void testCreateSavingsAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/savings-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"accountName\": \"John Doe\",\n" +
                        "  \"minRequiredBalance\": \"100.00\"\n" +
                        "}"))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, savingsAccountRepository.findAll().size());
        //Assertions.
    }


    @Test
    @Order(value = 2)
    void testGetSavingsAccounts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/savings-accounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @Order(value = 3)
    void testGetSavingsAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/savings-accounts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(value = 4)
    void testUpdateSavingsAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/savings-accounts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"accountName\": \"John Doe Jnr\",\n" +
                        "  \"minRequiredBalance\": \"100.00\"\n" +
                        "}"))
                .andExpect(status().isNoContent());
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
