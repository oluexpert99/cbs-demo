package com.cbs.democbs.exception;

import com.cbs.democbs.exception.core.AbstractPlatformResourceNotFoundException;
import java.io.Serial;

/** A {@link RuntimeException} thrown when savings Account  resources are not found. */
public class SavingsAccountTransactionNotFoundException extends AbstractPlatformResourceNotFoundException {

  @Serial private static final long serialVersionUID = -3426394758429956017L;

  public SavingsAccountTransactionNotFoundException(final Long id) {
    super("error.msg.savings.account.transaction.id.invalid", "Savings Account Transaction with identifier " + id + " does not exist");
  }

    public SavingsAccountTransactionNotFoundException(final Long id, final Long savingsAccountId) {
        super(
            "error.msg.savings.account.transaction.id.invalid",
            "Savings Account Transaction with identifier " + id + " does not exist for Savings Account with identifier " + savingsAccountId);
    }
}
