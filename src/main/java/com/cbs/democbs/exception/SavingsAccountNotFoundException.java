package com.cbs.democbs.exception;

import com.cbs.democbs.exception.core.AbstractPlatformResourceNotFoundException;
import java.io.Serial;


/** A {@link RuntimeException} thrown when savings Account  resources are not found. */
public class SavingsAccountNotFoundException extends AbstractPlatformResourceNotFoundException {

  @Serial private static final long serialVersionUID = -3426394758429956017L;

  public SavingsAccountNotFoundException(final Long id) {
    super("error.msg.savings.account.id.invalid", "Savings Account with identifier " + id + " does not exist");
  }

    public SavingsAccountNotFoundException(final String accountNumber) {
        super("error.msg.savings.account.accountNumber.invalid", "Savings Account with account number " + accountNumber + " does not exist");
    }
}
