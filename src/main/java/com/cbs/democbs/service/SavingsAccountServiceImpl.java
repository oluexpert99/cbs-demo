package com.cbs.democbs.service;

import com.cbs.democbs.domain.data.CreateSavingsAccountRequest;
import com.cbs.democbs.domain.data.CustomPageResponse;
import com.cbs.democbs.domain.data.SavingsAccountData;
import com.cbs.democbs.domain.model.SavingsAccount;
import com.cbs.democbs.domain.persistence.SavingsAccountRepository;
import com.cbs.democbs.exception.SavingsAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SavingsAccountServiceImpl implements SavingsAccountService {

  private final SavingsAccountRepository savingsAccountRepository;

  @Override
  public CustomPageResponse<SavingsAccountData> getAccounts(Pageable pageable) {
    Page<SavingsAccount> savingsAccountPage = savingsAccountRepository.findAll(pageable);
    List<SavingsAccountData> savingsAccountDataList =
        savingsAccountPage.getContent().stream().map(SavingsAccount::toData).toList();
    return CustomPageResponse.resolvePageResponse(
        savingsAccountDataList,
        savingsAccountPage.getTotalElements(),
        savingsAccountPage.getPageable());
  }

  @Override
  public SavingsAccount getAccountByAccountNumber(String accountNumber) {
    SavingsAccount savingsAccount = savingsAccountRepository.findFirstByAccountNumber(accountNumber);
    if (savingsAccount == null) {
      throw new SavingsAccountNotFoundException(accountNumber);
    }
    return savingsAccount;
  }


  @Override
  public SavingsAccountData getAccountData(Long savingsAccountId) {
    SavingsAccount savingsAccount = getAccount(savingsAccountId);
    return savingsAccount.toData();
  }

  @Override
  public SavingsAccount getAccount(Long savingsAccountId) {
    return savingsAccountRepository.findById(savingsAccountId).orElseThrow(() -> new SavingsAccountNotFoundException(savingsAccountId));
  }

  @Override
  public void deleteAccountByAccountNumber(String accountNumber) {
    SavingsAccount savingsAccount = getAccountByAccountNumber(accountNumber);
    savingsAccount.delete();
    // soft delete the account
    savingsAccountRepository.save(savingsAccount);
  }

  @Override
  public void deleteAccount(long savingsAccountId) {
    SavingsAccount savingsAccount = getAccount(savingsAccountId);
    if (!savingsAccount.isDeleted()){
      savingsAccount.delete();
      // soft delete the account
      savingsAccountRepository.save(savingsAccount);
    }


  }

  @Override
  public SavingsAccountData createAccount(CreateSavingsAccountRequest request) {
    // create a new account
    SavingsAccount savingsAccount = SavingsAccount.newAccount(request);
    savingsAccount = savingsAccountRepository.save(savingsAccount);
    return savingsAccount.toData();
  }

  @Override
  public void updateAccount(Long savingsAccountId, CreateSavingsAccountRequest request) {
    SavingsAccount savingsAccount = getAccount(savingsAccountId);
    if (StringUtils.isNotBlank(request.getAccountName())){
        // update the account name
        savingsAccount.setAccountName(request.getAccountName());
    }
    // update the min required balance
    if (request.getMinRequiredBalance() != null){
        savingsAccount.setMinRequiredBalance(request.getMinRequiredBalance());
    }
    savingsAccountRepository.save(savingsAccount);
  }
}
