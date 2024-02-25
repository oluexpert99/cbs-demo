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
public class CreateSavingsAccountRequest {



  private String accountName;

  private BigDecimal minRequiredBalance;


}
