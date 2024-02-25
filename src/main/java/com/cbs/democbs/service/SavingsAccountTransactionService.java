package com.cbs.democbs.service;

import com.cbs.democbs.domain.data.CreateSavingsAccountTransactionRequest;
import com.cbs.democbs.domain.data.CustomPageResponse;
import com.cbs.democbs.domain.data.SavingsAccountTransactionData;
import org.springframework.data.domain.Pageable;

public interface SavingsAccountTransactionService {

 CustomPageResponse<SavingsAccountTransactionData> getTransactionsForAccount(Long savingsAccountId, Pageable pageable);
 SavingsAccountTransactionData getTransactionData(Long transactionId, Long savingsAccountId);

    SavingsAccountTransactionData createTransaction(Long savingsAccountId, CreateSavingsAccountTransactionRequest request );

    void undoTransaction(Long savingsAccountId, Long transactionId);


}
