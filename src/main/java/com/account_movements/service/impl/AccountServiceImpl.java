package com.account_movements.service.impl;

import com.account_movements.dto.request.CreateAccountRequestDto;
import com.account_movements.dto.request.UpdateAccountRequestDto;
import com.account_movements.dto.response.AccountResponseDto;
import com.account_movements.exception.CustomException;
import com.account_movements.model.Account;
import com.account_movements.repository.AccountRepository;
import com.account_movements.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Mono<AccountResponseDto> createAccount(CreateAccountRequestDto requestDto) {
        Account account = new Account();
        account.setAccountNumber(requestDto.getAccountNumber());
        account.setAccountType(requestDto.getAccountType());
        account.setInitialBalance(requestDto.getInitialBalance());
        account.setStatus(requestDto.getStatus());

        return Mono.just(accountRepository.save(account))
                .map(savedAccount -> new AccountResponseDto(
                        savedAccount.getId(),
                        savedAccount.getAccountNumber(),
                        savedAccount.getAccountType(),
                        savedAccount.getInitialBalance(),
                        savedAccount.getStatus()
                ));
    }

    @Override
    public Mono<AccountResponseDto> updateAccount(UpdateAccountRequestDto requestDto) {
        return Mono.justOrEmpty(accountRepository.findById(requestDto.getId()))
                .switchIfEmpty(Mono.error(new CustomException("Account not found")))
                .flatMap(account -> {
                    account.setAccountNumber(requestDto.getAccountNumber());
                    account.setAccountType(requestDto.getAccountType());
                    account.setInitialBalance(requestDto.getInitialBalance());
                    account.setStatus(requestDto.getStatus());
                    return Mono.just(accountRepository.save(account))
                            .map(updatedAccount -> new AccountResponseDto(
                                    updatedAccount.getId(),
                                    updatedAccount.getAccountNumber(),
                                    updatedAccount.getAccountType(),
                                    updatedAccount.getInitialBalance(),
                                    updatedAccount.getStatus()
                            ));
                });
    }

    @Override
    public Mono<AccountResponseDto> getAccountById(Long id) {
        return Mono.justOrEmpty(accountRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Account not found")))
                .map(account -> new AccountResponseDto(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getInitialBalance(),
                        account.getStatus()
                ));
    }

    @Override
    public Flux<AccountResponseDto> getAllAccounts() {
        return Flux.fromIterable(accountRepository.findAll())
                .map(account -> new AccountResponseDto(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getInitialBalance(),
                        account.getStatus()
                ));
    }

    @Override
    public Mono<Void> deleteAccount(Long id) {
        return Mono.justOrEmpty(accountRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Account not found")))
                .flatMap(account -> {
                    accountRepository.delete(account);
                    return Mono.empty();
                });
    }
}
