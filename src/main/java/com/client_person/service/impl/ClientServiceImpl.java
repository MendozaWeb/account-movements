package com.client_person.service.impl;

import com.client_person.dto.request.CreateClientRequestDto;
import com.client_person.dto.response.ClientResponseDto;
import com.client_person.exception.CustomException;
import com.client_person.model.Client;
import com.client_person.model.Person;
import com.client_person.repository.ClientRepository;
import com.client_person.repository.PersonRepository;
import com.client_person.service.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Mono<ClientResponseDto> createClient(CreateClientRequestDto requestDto) {
        return Mono.justOrEmpty(personRepository.findById(requestDto.getPersonId()))
                .switchIfEmpty(Mono.error(new CustomException("Person not found")))
                .flatMap(person -> {
                    Client client = new Client();
                    client.setClientId(requestDto.getClientId());
                    client.setPassword(requestDto.getPassword());
                    client.setStatus(requestDto.isStatus());
                    client.setPerson(person);
                    return Mono.just(clientRepository.save(client))
                            .map(savedClient -> new ClientResponseDto(savedClient.getClientId(), savedClient.isStatus()));
                });
    }


    @Override
    public Flux<ClientResponseDto> getAllClients() {
        return Flux.defer(() -> Flux.fromIterable(clientRepository.findAll()))
                .map(client -> new ClientResponseDto(client.getClientId(), client.isStatus()));
    }

    @Override
    public Mono<ClientResponseDto> getClientById(Long id) {
        return Mono.justOrEmpty(clientRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Client not found")))
                .map(client -> new ClientResponseDto(client.getClientId(), client.isStatus()));
    }

    @Override
    public Mono<ClientResponseDto> updateClient(Long id, CreateClientRequestDto requestDto) {
        return Mono.defer(() -> Mono.justOrEmpty(clientRepository.findById(id)))
                .switchIfEmpty(Mono.error(new CustomException("Client not found")))
                .flatMap(client -> {
                    client.setClientId(requestDto.getClientId());
                    client.setPassword(requestDto.getPassword());
                    client.setStatus(requestDto.isStatus());
                    return Mono.fromCallable(() -> clientRepository.save(client))
                            .map(updatedClient -> new ClientResponseDto(updatedClient.getClientId(), updatedClient.isStatus()));
                });
    }

    @Override
    public Mono<Void> deleteClient(Long id) {
        return Mono.justOrEmpty(clientRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Client not found")))
                .flatMap(client -> {
                    clientRepository.delete(client);
                    return Mono.empty();
                });
    }

}

