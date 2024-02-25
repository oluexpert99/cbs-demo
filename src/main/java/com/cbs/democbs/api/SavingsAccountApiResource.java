package com.cbs.democbs.api;

import com.cbs.democbs.domain.data.CreateSavingsAccountRequest;
import com.cbs.democbs.domain.data.CustomPageResponse;
import com.cbs.democbs.domain.data.SavingsAccountData;
import com.cbs.democbs.service.SavingsAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/savings-accounts")
@RequiredArgsConstructor
public class SavingsAccountApiResource {
  private final SavingsAccountService savingsAccountService;

  @GetMapping
  public ResponseEntity<CustomPageResponse<SavingsAccountData>> retrieveSavingsAccounts(
      Pageable pageable) {
    return ResponseEntity.ok(savingsAccountService.getAccounts(pageable));
  }

  @GetMapping("/{savingsAccountId}")
  public ResponseEntity<SavingsAccountData> retrieveSavingsAccount(
      @PathVariable Long savingsAccountId) {
    return ResponseEntity.ok(savingsAccountService.getAccountData(savingsAccountId));
  }

  @PostMapping
  public ResponseEntity<SavingsAccountData> createSavingsAccount(
      @RequestBody CreateSavingsAccountRequest request) {
    SavingsAccountData newAccount = savingsAccountService.createAccount(request);
    URI uri =
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{savingsAccountId}")
                    .buildAndExpand(id, newAccount.getId())
                    .toUri();
    return ResponseEntity.created(uri).body(newAccount);
  }

  @PutMapping("/{savingsAccountId}")
  public ResponseEntity<SavingsAccountData> updateSavingsAccount(
      @PathVariable Long savingsAccountId, @RequestBody CreateSavingsAccountRequest request) {
    savingsAccountService.updateAccount(savingsAccountId, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{savingsAccountId}")
    public ResponseEntity<Void> deleteSavingsAccount(@PathVariable Long savingsAccountId) {
        savingsAccountService.deleteAccount(savingsAccountId);
        return ResponseEntity.noContent().build();
    }
}
