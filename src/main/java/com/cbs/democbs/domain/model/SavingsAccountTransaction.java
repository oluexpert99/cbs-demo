package com.cbs.democbs.domain.model;


import com.cbs.democbs.domain.data.SavingsAccountTransactionData;
import com.cbs.democbs.domain.data.TransactionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cbs_savings_account_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "instance")
public class SavingsAccountTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "savings_account_id", referencedColumnName = "id", nullable = false)
    private SavingsAccount savingsAccount;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "transaction_description", length = 100, nullable = false)
    private String transactionDescription;


    @Column(name = "amount", scale = 6, precision = 19, nullable = false)
    private BigDecimal amount;

    @Column(name = "is_reversed", nullable = false)
    private boolean reversed;

    @Column(name = "is_transfer", nullable = false)
    private boolean transfer;

    @Column(name = "reversed_transaction_id")
    private Long  reversedTransactionId;



    public void reverse(){
        this.reversed = true;
    }

    public SavingsAccountTransactionData toData(){
        return SavingsAccountTransactionData.instance(
                this.getId(),
                this.getSavingsAccount().toData(),
                this.getTransactionType(),
                this.getAmount(),
                this.isReversed(),
                this.getTransactionDescription(),
                this.getReversedTransactionId(),
                this.getCreatedAt(),
                this.getLastModifiedAt(),
                this.getCreatedBy(),
                this.getLastModifiedBy()
        );
    }


    public void deposit(SavingsAccount savingsAccount, BigDecimal amount, String transactionDescription) {
        this.savingsAccount = savingsAccount;
        this.transactionType = TransactionType.DEPOSIT;
        this.amount = amount;
        this.transactionDescription = transactionDescription;
    }

    public void withdrawal(SavingsAccount savingsAccount, BigDecimal amount, String transactionDescription) {
        this.savingsAccount = savingsAccount;
        this.transactionType = TransactionType.WITHDRAWAL;
        this.amount = amount;
        this.transactionDescription = transactionDescription;
    }

    public boolean isDebit(){
        return transactionType == TransactionType.WITHDRAWAL || transactionType == TransactionType.TRANSFER;
    }
    public boolean isCredit() {
        return  TransactionType.DEPOSIT == transactionType;
    }
}
