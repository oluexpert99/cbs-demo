package com.cbs.democbs.service;

import com.cbs.democbs.domain.data.CreateSavingsAccountTransactionRequest;
import com.cbs.democbs.domain.data.CustomPageResponse;
import com.cbs.democbs.domain.data.SavingsAccountTransactionData;
import com.cbs.democbs.domain.data.TransactionType;
import com.cbs.democbs.domain.model.SavingsAccount;
import com.cbs.democbs.domain.model.SavingsAccountTransaction;
import com.cbs.democbs.domain.persistence.SavingsAccountTransactionRepository;
import com.cbs.democbs.exception.SavingsAccountNotFoundException;
import com.cbs.democbs.exception.SavingsAccountTransactionNotFoundException;
import com.cbs.democbs.exception.SavingsAccountTransactionValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SavingsAccountTransactionServiceImpl implements SavingsAccountTransactionService {

  private final SavingsAccountService savingsAccountService;
  private final SavingsAccountTransactionRepository savingsAccountTransactionRepository;
  private static final String FROM_ACCOUNT = "From Account";

  @Override
  public CustomPageResponse<SavingsAccountTransactionData> getTransactionsForAccount(
      Long savingsAccountId, Pageable pageable) {
    Page<SavingsAccountTransaction> transactions =
        savingsAccountTransactionRepository.findBySavingsAccountId(savingsAccountId, pageable);
    List<SavingsAccountTransactionData> transactionDataList =
        transactions.getContent().stream().map(SavingsAccountTransaction::toData).toList();
    return CustomPageResponse.resolvePageResponse(
        transactionDataList, transactions.getTotalElements(), transactions.getPageable());
  }

  @Override
  public SavingsAccountTransactionData getTransactionData(
      Long transactionId, Long savingsAccountId) {
    return getTransaction(transactionId, savingsAccountId).toData();
  }

  private SavingsAccountTransaction getTransaction(Long transactionId, Long savingsAccountId) {
    SavingsAccountTransaction savingsAccountTransaction =
        savingsAccountTransactionRepository.findByIdAndSavingsAccountId(
            transactionId, savingsAccountId);
    if (savingsAccountTransaction == null) {
      throw new SavingsAccountTransactionNotFoundException(transactionId, savingsAccountId);
    }
    return savingsAccountTransaction;
  }

  @Override
  public SavingsAccountTransactionData createTransaction(
      Long savingsAccountId, CreateSavingsAccountTransactionRequest request) {

    TransactionType transactionType = request.getTransactionType();
      return switch (transactionType) {
          case DEPOSIT -> handleDeposit(savingsAccountId, request);
          case WITHDRAWAL -> handleWithdrawal(savingsAccountId, request);
          case TRANSFER -> handleTransfer(savingsAccountId, request);
          default -> throw new SavingsAccountTransactionValidationException(
                  "Unsupported transaction type: " + transactionType);
      };
  }

  private SavingsAccountTransactionData handleDeposit(
      Long savingsAccountId, CreateSavingsAccountTransactionRequest request) {
    SavingsAccount fromAccount = getSavingsAccountForTransaction(savingsAccountId, FROM_ACCOUNT);
    SavingsAccountTransaction savingsAccountTransaction = new SavingsAccountTransaction();
    validateDepositAmount(request.getAmount());

    savingsAccountTransaction.deposit(fromAccount, request.getAmount(), request.getDescription());
    savingsAccountTransaction = savingsAccountTransactionRepository.save(savingsAccountTransaction);
    return savingsAccountTransaction.toData();
  }

  private SavingsAccountTransactionData handleWithdrawal(
      Long savingsAccountId, CreateSavingsAccountTransactionRequest request) {
    SavingsAccount fromAccount = getSavingsAccountForTransaction(savingsAccountId, FROM_ACCOUNT);
    validateWithdrawal(fromAccount, request);
    SavingsAccountTransaction savingsAccountTransaction = new SavingsAccountTransaction();
    savingsAccountTransaction.withdrawal(
        fromAccount, request.getAmount(), request.getDescription());
    savingsAccountTransaction = savingsAccountTransactionRepository.save(savingsAccountTransaction);
    return savingsAccountTransaction.toData();
  }

  private void validateWithdrawal(
      SavingsAccount fromAccount, CreateSavingsAccountTransactionRequest request) {
    BigDecimal amount = request.getAmount();
    if (fromAccount.getBalance().compareTo(amount) < 0) {
      throw new SavingsAccountTransactionValidationException(
          "Insufficient balance for withdrawal transaction");
    }
    if (fromAccount.getBalance().subtract(amount).compareTo(fromAccount.getMinRequiredBalance())
        < 0) {
      throw new SavingsAccountTransactionValidationException(
          "Insufficient balance for withdrawal transaction");
    }
  }

  private void validateDepositAmount(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new SavingsAccountTransactionValidationException(
          "Deposit amount must be greater than zero");
    }
  }

  private SavingsAccountTransactionData handleTransfer(
      Long fromSavingsAccountId, CreateSavingsAccountTransactionRequest request) {
    // Retrieve savings accounts
    SavingsAccount fromAccount =
        getSavingsAccountForTransaction(fromSavingsAccountId, FROM_ACCOUNT);
    SavingsAccount toAccount =
        getSavingsAccountForTransaction(request.getToAccountId(), "To Account");
    validateDepositAmount(request.getAmount());
    validateWithdrawal(fromAccount, request);

    // Withdraw from the source account
    SavingsAccountTransaction debitTransaction = new SavingsAccountTransaction();

    debitTransaction.withdrawal(fromAccount, request.getAmount(), request.getDescription());
    debitTransaction.setTransfer(true);

    // Deposit into the destination account
    SavingsAccountTransaction creditTransaction = new SavingsAccountTransaction();
    creditTransaction.deposit(toAccount, request.getAmount(), request.getDescription());
    creditTransaction.setTransfer(true);

    // Save transactions
    List<SavingsAccountTransaction> transactions = List.of(debitTransaction, creditTransaction);
    savingsAccountTransactionRepository.saveAll(transactions);

    // Return data of the debited transaction as the response
    return debitTransaction.toData();
  }

  @Override
  public void undoTransaction(Long savingsAccountId, Long transactionId) {
    SavingsAccountTransaction savingsAccountTransaction =
        getTransaction(transactionId, savingsAccountId);
    if (savingsAccountTransaction.isReversed()) {
      throw new SavingsAccountTransactionValidationException(
          "Transaction has already been reversed");
    }
    if (savingsAccountTransaction.isTransfer()) {
      throw new SavingsAccountTransactionValidationException(
          "Transaction cannot be reversed as it is a transfer transaction");
    }
    SavingsAccountTransaction savingsAccountTransactionReversed = getAccountTransactionReversed(savingsAccountTransaction);
    savingsAccountTransactionRepository.save(savingsAccountTransactionReversed);
    savingsAccountTransaction.reverse();
    savingsAccountTransactionRepository.save(savingsAccountTransaction);
  }

  private  SavingsAccountTransaction getAccountTransactionReversed(SavingsAccountTransaction savingsAccountTransaction) {
    SavingsAccountTransaction savingsAccountTransactionReversed = new SavingsAccountTransaction();
    TransactionType reveralTransactionType =
        savingsAccountTransaction.isDebit() ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
    savingsAccountTransactionReversed.setSavingsAccount(
        savingsAccountTransaction.getSavingsAccount());
    savingsAccountTransactionReversed.setTransactionType(reveralTransactionType);
    savingsAccountTransactionReversed.setAmount(savingsAccountTransaction.getAmount());
    savingsAccountTransactionReversed.setTransactionDescription(
            "Reversal of " + savingsAccountTransaction.getTransactionDescription());
    savingsAccountTransactionReversed.setReversedTransactionId(savingsAccountTransaction.getId());
    return savingsAccountTransactionReversed;
  }

  private SavingsAccount getSavingsAccountForTransaction(
      Long savingsAccountId, String accountType) {
    SavingsAccount savingsAccount;
    try {
      savingsAccount = savingsAccountService.getAccount(savingsAccountId);

    } catch (SavingsAccountNotFoundException e) {
      throw new SavingsAccountTransactionValidationException(
          String.format("%s account not found", accountType));
    }
    if (savingsAccount.isDeleted()) {
      throw new SavingsAccountTransactionValidationException(
          String.format(
              " Transaction not allowed on %s account as it has been deleted ", accountType));
    }

    return savingsAccount;
  }
}
