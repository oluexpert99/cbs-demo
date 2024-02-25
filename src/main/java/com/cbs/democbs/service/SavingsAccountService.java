package com.cbs.democbs.service;

import com.cbs.democbs.domain.data.CreateSavingsAccountRequest;
import com.cbs.democbs.domain.data.CustomPageResponse;
import com.cbs.democbs.domain.data.SavingsAccountData;
import com.cbs.democbs.domain.model.SavingsAccount;
import org.springframework.data.domain.Pageable;

public interface SavingsAccountService {

  CustomPageResponse<SavingsAccountData> getAccounts(Pageable pageable);

  SavingsAccount getAccountByAccountNumber(String accountNumber);

  SavingsAccountData getAccountData(Long savingsAccountId);

  SavingsAccount getAccount(Long savingsAccountId);

  void deleteAccountByAccountNumber(String accountNumber);

  void deleteAccount(long savingsAccountId);

  SavingsAccountData createAccount(CreateSavingsAccountRequest request);

  void updateAccount(Long savingsAccountId, CreateSavingsAccountRequest request);
}
