package com.cbs.democbs.exception;


import com.cbs.democbs.exception.core.AbstractPlatformDomainRuleException;

import java.io.Serial;

/** User: ayoade_farooq@yahoo.com Date: 22/05/2023 Time: 22:50 */
public class SavingsAccountTransactionValidationException extends AbstractPlatformDomainRuleException {

  @Serial private static final long serialVersionUID = 8201072163786590208L;

  public SavingsAccountTransactionValidationException(final String message) {
    super("error.msg.savings.account.transactions.value.invalid", message);
  }
}
