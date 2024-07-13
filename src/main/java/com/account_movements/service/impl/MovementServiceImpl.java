package com.account_movements.service.impl;

import com.account_movements.dto.request.CreateMovementRequestDto;
import com.account_movements.dto.request.UpdateMovementRequestDto;
import com.account_movements.dto.response.MovementResponseDto;
import com.account_movements.exception.CustomException;
import com.account_movements.model.Account;
import com.account_movements.model.Movement;
import com.account_movements.repository.AccountRepository;
import com.account_movements.repository.MovementRepository;
import com.account_movements.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Mono<MovementResponseDto> createMovement(CreateMovementRequestDto requestDto) {
        return Mono.justOrEmpty(accountRepository.findById(requestDto.getAccountId()))
                .switchIfEmpty(Mono.error(new CustomException("Account not found")))
                .flatMap(account -> {
                    Movement movement = new Movement();
                    movement.setDate(requestDto.getDate());
                    movement.setMovementType(requestDto.getMovementType());
                    movement.setValue(requestDto.getValue());
                    movement.setAccount(account);

                    double newBalance = account.getInitialBalance() + requestDto.getValue();
                    if (newBalance < 0) {
                        return Mono.error(new CustomException("Saldo no disponible"));
                    }
                    movement.setBalance(newBalance);
                    account.setInitialBalance(newBalance);

                    accountRepository.save(account);
                    return Mono.just(movementRepository.save(movement))
                            .map(savedMovement -> new MovementResponseDto(
                                    savedMovement.getId(),
                                    savedMovement.getDate(),
                                    savedMovement.getMovementType(),
                                    savedMovement.getValue(),
                                    savedMovement.getBalance(),
                                    savedMovement.getAccount().getId()
                            ));
                });
    }

    @Override
    public Mono<MovementResponseDto> updateMovement(UpdateMovementRequestDto requestDto) {
        return Mono.justOrEmpty(movementRepository.findById(requestDto.getId()))
                .switchIfEmpty(Mono.error(new CustomException("Movement not found")))
                .flatMap(movement -> {
                    movement.setDate(requestDto.getDate());
                    movement.setMovementType(requestDto.getMovementType());
                    movement.setValue(requestDto.getValue());

                    Account account = movement.getAccount();
                    double newBalance = account.getInitialBalance() + requestDto.getValue();
                    if (newBalance < 0) {
                        return Mono.error(new CustomException("Saldo no disponible"));
                    }
                    movement.setBalance(newBalance);
                    account.setInitialBalance(newBalance);
                    accountRepository.save(account);
                    return Mono.just(movementRepository.save(movement))
                            .map(updatedMovement -> new MovementResponseDto(
                                    updatedMovement.getId(),
                                    updatedMovement.getDate(),
                                    updatedMovement.getMovementType(),
                                    updatedMovement.getValue(),
                                    updatedMovement.getBalance(),
                                    updatedMovement.getAccount().getId()
                            ));
                });
    }

    @Override
    public Mono<MovementResponseDto> getMovementById(Long id) {
        return Mono.justOrEmpty(movementRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Movement not found")))
                .map(movement -> new MovementResponseDto(
                        movement.getId(),
                        movement.getDate(),
                        movement.getMovementType(),
                        movement.getValue(),
                        movement.getBalance(),
                        movement.getAccount().getId()
                ));
    }

    @Override
    public Flux<MovementResponseDto> getAllMovements() {
        return Flux.fromIterable(movementRepository.findAll())
                .map(movement -> new MovementResponseDto(
                        movement.getId(),
                        movement.getDate(),
                        movement.getMovementType(),
                        movement.getValue(),
                        movement.getBalance(),
                        movement.getAccount().getId()
                ));
    }

    @Override
    public Mono<Void> deleteMovement(Long id) {
        return Mono.justOrEmpty(movementRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Movement not found")))
                .flatMap(movement -> {
                    movementRepository.delete(movement);
                    return Mono.empty();
                });
    }
}
