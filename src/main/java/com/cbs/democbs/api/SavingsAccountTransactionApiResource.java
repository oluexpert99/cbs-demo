package com.cbs.democbs.api;

import com.cbs.democbs.domain.data.CreateSavingsAccountTransactionRequest;
import com.cbs.democbs.domain.data.CustomPageResponse;
import com.cbs.democbs.domain.data.SavingsAccountTransactionData;
import com.cbs.democbs.service.SavingsAccountTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/savings-accounts/{savingsId}/transactions")
@RequiredArgsConstructor
public class SavingsAccountTransactionApiResource {

  private final SavingsAccountTransactionService savingsAccountTransactionService;

  @GetMapping
  public ResponseEntity<CustomPageResponse<SavingsAccountTransactionData>> retrieveTransactions(
      @PathVariable Long savingsId, Pageable pageable) {

    return ResponseEntity.ok(
        savingsAccountTransactionService.getTransactionsForAccount(savingsId, pageable));
  }

  @GetMapping("/{transactionId}")
  public ResponseEntity<SavingsAccountTransactionData> retrieveTransaction(
      @PathVariable Long savingsId, @PathVariable Long transactionId) {
    return ResponseEntity.ok(
        savingsAccountTransactionService.getTransactionData(transactionId, savingsId));
  }

  @PutMapping("/{transactionId}")
  public ResponseEntity<SavingsAccountTransactionData> reverseTransaction(
      @PathVariable Long savingsId, @PathVariable Long transactionId) {
    savingsAccountTransactionService.undoTransaction( savingsId,transactionId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
    public ResponseEntity<SavingsAccountTransactionData> createTransaction(
        @PathVariable Long savingsId, @RequestBody CreateSavingsAccountTransactionRequest request) {
        return ResponseEntity.ok(savingsAccountTransactionService.createTransaction(savingsId, request));
    }
}
