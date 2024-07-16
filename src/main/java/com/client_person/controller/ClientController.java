package com.client_person.controller;

import com.client_person.dto.request.CreateClientRequestDto;
import com.client_person.dto.response.ClientResponseDto;
import com.client_person.service.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public Mono<ResponseEntity<ClientResponseDto>> createClient(@RequestBody CreateClientRequestDto requestDto) {
        return clientService.createClient(requestDto)
                .map(responseDto -> ResponseEntity.status(201).body(responseDto));
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<List<ClientResponseDto>>> getAllClients() {
        return clientService.getAllClients()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/list/{id}")
    public Mono<ResponseEntity<ClientResponseDto>> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<ClientResponseDto>> updateClient(@PathVariable Long id, @RequestBody CreateClientRequestDto requestDto) {
        return clientService.updateClient(id, requestDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

}
