package com.client_person.service.interfaces;

import com.client_person.dto.request.CreatePersonRequestDto;
import com.client_person.dto.response.PersonResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PersonService {
    Mono<PersonResponseDto> createPerson(CreatePersonRequestDto requestDto);
    Flux<PersonResponseDto> getAllPersons();
    Mono<PersonResponseDto> getPersonById(Long id);
    Mono<PersonResponseDto> updatePerson(Long id, CreatePersonRequestDto requestDto);
    Mono<Void> deletePerson(Long id);
}
