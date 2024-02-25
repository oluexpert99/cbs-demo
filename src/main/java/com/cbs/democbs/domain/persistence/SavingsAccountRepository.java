package com.cbs.democbs.domain.persistence;

import com.cbs.democbs.domain.model.SavingsAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsAccountRepository extends BaseRepository<SavingsAccount,Long> {
    SavingsAccount findFirstByAccountNumber(String accountNumber);
}
