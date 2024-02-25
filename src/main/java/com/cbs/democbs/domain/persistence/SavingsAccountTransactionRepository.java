package com.cbs.democbs.domain.persistence;

import com.cbs.democbs.domain.model.SavingsAccountTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsAccountTransactionRepository extends  BaseRepository<SavingsAccountTransaction,Long> {
    Page<SavingsAccountTransaction> findBySavingsAccountId(Long savingsAccountId, Pageable pageable);
    SavingsAccountTransaction findByIdAndSavingsAccountId(Long id, Long savingsAccountId);

    Long countBySavingsAccountId(Long savingsAccountId);
}
