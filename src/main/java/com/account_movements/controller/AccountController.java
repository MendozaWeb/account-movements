package com.account_movements.controller;

import com.account_movements.dto.request.CreateAccountRequestDto;
import com.account_movements.dto.request.UpdateAccountRequestDto;
import com.account_movements.dto.response.AccountResponseDto;
import com.account_movements.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public Mono<ResponseEntity<AccountResponseDto>> createAccount(@RequestBody CreateAccountRequestDto requestDto) {
        return accountService.createAccount(requestDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<AccountResponseDto>> updateAccount(@RequestBody UpdateAccountRequestDto requestDto) {
        return accountService.updateAccount(requestDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountResponseDto>> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public Flux<AccountResponseDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccount(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
