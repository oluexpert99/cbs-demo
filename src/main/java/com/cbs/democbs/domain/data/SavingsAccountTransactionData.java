package com.cbs.democbs.domain.data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "instance")
public class SavingsAccountTransactionData {

  private Long id;

  private SavingsAccountData savingsAccount;

  private TransactionType transactionType;

  private BigDecimal amount;

  private boolean reversed;
  private String transactionDescription;
  private Long  reversedTransactionId;

  private LocalDateTime createdAt;

  private LocalDateTime lastModifiedAt;

  private String createdBy;

  private String lastModifiedBy;
}
