package com.account_movements.service;

import com.account_movements.dto.request.CreateAccountRequestDto;
import com.account_movements.dto.request.UpdateAccountRequestDto;
import com.account_movements.dto.response.AccountResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<AccountResponseDto> createAccount(CreateAccountRequestDto requestDto);
    Mono<AccountResponseDto> updateAccount(UpdateAccountRequestDto requestDto);
    Mono<AccountResponseDto> getAccountById(Long id);
    Flux<AccountResponseDto> getAllAccounts();
    Mono<Void> deleteAccount(Long id);
}
