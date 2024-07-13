package com.account_movements.service;

import com.account_movements.dto.request.CreateMovementRequestDto;
import com.account_movements.dto.request.UpdateMovementRequestDto;
import com.account_movements.dto.response.MovementResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Mono<MovementResponseDto> createMovement(CreateMovementRequestDto requestDto);
    Mono<MovementResponseDto> updateMovement(UpdateMovementRequestDto requestDto);
    Mono<MovementResponseDto> getMovementById(Long id);
    Flux<MovementResponseDto> getAllMovements();
    Mono<Void> deleteMovement(Long id);
}
