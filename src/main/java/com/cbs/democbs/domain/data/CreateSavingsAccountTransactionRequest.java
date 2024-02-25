package com.cbs.democbs.domain.data;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "instance")
public class CreateSavingsAccountTransactionRequest {


    private Long toAccountId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String description;

}
