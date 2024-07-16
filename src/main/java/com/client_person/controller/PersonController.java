package com.client_person.controller;

import com.client_person.dto.request.CreatePersonRequestDto;
import com.client_person.dto.response.PersonResponseDto;
import com.client_person.exception.CustomException;
import com.client_person.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/create")
    public Mono<ResponseEntity<PersonResponseDto>> createPerson(@RequestBody CreatePersonRequestDto requestDto) {
        return personService.createPerson(requestDto)
                .map(responseDto -> ResponseEntity.status(201).body(responseDto));
    }

    @GetMapping("/list")
    public Flux<PersonResponseDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/list/{id}")
    public Mono<ResponseEntity<PersonResponseDto>> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<PersonResponseDto>> updatePerson(@PathVariable Long id, @RequestBody CreatePersonRequestDto requestDto) {
        return personService.updatePerson(id, requestDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable Long id) {
        return personService.deletePerson(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

}
