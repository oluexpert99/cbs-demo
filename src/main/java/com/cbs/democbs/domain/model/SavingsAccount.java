package com.cbs.democbs.domain.model;

import com.cbs.democbs.domain.data.CreateSavingsAccountRequest;
import com.cbs.democbs.domain.data.SavingsAccountData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * SavingsAccount that handles both savings and current accounts
 * and currency is NAIRA for now
 */
@Entity
@Table(name = "cbs_savings_account")
@Getter
@Setter
@NoArgsConstructor
public class SavingsAccount extends BaseEntity{
    
    private static final Random random = new Random();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "account_number", length = 20, unique = true, nullable = false)
    protected String accountNumber;


    @Column(name = "account_name", nullable = false)
    protected String accountName;

    @Column(name = "min_required_balance", scale = 6, precision = 19, nullable = true)
    private BigDecimal minRequiredBalance;

    @Column(name = "del_flag", nullable = false)
    protected String delFlag = "N";

    @OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL)
    private List<SavingsAccountTransaction> transactions = new ArrayList<>();


    public void addTransaction(SavingsAccountTransaction transaction){
        transactions.add(transaction);
        transaction.setSavingsAccount(this);
    }
    public SavingsAccountData toData(){
        return SavingsAccountData.instance(

                this.getId(),
                this.getAccountNumber(),
                this.getAccountName(),
                this.getMinRequiredBalance(),
                this.delFlag,
                this.getBalance(),
                this.getCreatedAt(),
                this.getLastModifiedAt(),
                this.getCreatedBy(),
                this.getLastModifiedBy());
    }

    public void delete(){
        this.delFlag = "Y";
    }
    public static SavingsAccount newAccount (CreateSavingsAccountRequest request){
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountNumber( generateAccountNumber());
        savingsAccount.setAccountName(request.getAccountName());
        savingsAccount.setMinRequiredBalance(request.getMinRequiredBalance());
        return savingsAccount;
    }
    private static String generateAccountNumber(){
      
        return StringUtils.leftPad(String.valueOf((random.nextInt() * 1000000)), 10, "0");
    }
    public boolean isDeleted(){
        return "Y".equalsIgnoreCase(this.delFlag);
    }

    public BigDecimal getBalance() {
return   transactions.stream()
                .map(transaction -> transaction.isDebit() ? transaction.getAmount().negate() : transaction.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);


    }
}
