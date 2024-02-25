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
public class SavingsAccountData {

  private Long id;

  protected String accountNumber;

  protected String accountName;

  private BigDecimal minRequiredBalance;

  private String delFlag ;
  private BigDecimal balance;

  private LocalDateTime createdAt;

  private LocalDateTime lastModifiedAt;

  private String createdBy;

  private String lastModifiedBy;
}
