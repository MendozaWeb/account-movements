package com.account_movements.controller;


import com.account_movements.dto.request.CreateMovementRequestDto;
import com.account_movements.dto.request.UpdateMovementRequestDto;
import com.account_movements.dto.response.MovementResponseDto;
import com.account_movements.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @PostMapping("/create")
    public Mono<ResponseEntity<MovementResponseDto>> createMovement(@RequestBody CreateMovementRequestDto requestDto) {
        return movementService.createMovement(requestDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<MovementResponseDto>> updateMovement(@RequestBody UpdateMovementRequestDto requestDto) {
        return movementService.updateMovement(requestDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MovementResponseDto>> getMovementById(@PathVariable Long id) {
        return movementService.getMovementById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public Flux<MovementResponseDto> getAllMovements() {
        return movementService.getAllMovements();
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteMovement(@PathVariable Long id) {
        return movementService.deleteMovement(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
