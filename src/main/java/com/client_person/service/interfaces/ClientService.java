package com.client_person.service.interfaces;

import com.client_person.dto.request.CreateClientRequestDto;
import com.client_person.dto.response.ClientResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<ClientResponseDto> createClient(CreateClientRequestDto requestDto);
    Flux<ClientResponseDto> getAllClients();
    Mono<ClientResponseDto> getClientById(Long id);
    Mono<ClientResponseDto> updateClient(Long id, CreateClientRequestDto requestDto);
    Mono<Void> deleteClient(Long id);
}
