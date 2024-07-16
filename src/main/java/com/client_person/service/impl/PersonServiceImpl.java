package com.client_person.service.impl;

import com.client_person.dto.request.CreatePersonRequestDto;
import com.client_person.dto.response.PersonResponseDto;
import com.client_person.exception.CustomException;
import com.client_person.model.Person;
import com.client_person.repository.PersonRepository;
import com.client_person.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public Mono<PersonResponseDto> createPerson(CreatePersonRequestDto requestDto) {
        Person person = new Person();
        person.setName(requestDto.getName());
        person.setGender(requestDto.getGender());
        person.setAge(requestDto.getAge());
        person.setAddress(requestDto.getAddress());
        person.setPhone(requestDto.getPhone());
        return Mono.just(personRepository.save(person))
                .map(savedPerson -> new PersonResponseDto(savedPerson.getId(), savedPerson.getName(), savedPerson.getGender(),
                        savedPerson.getAge(), savedPerson.getAddress(), savedPerson.getPhone()));

    }

    @Override
    public Flux<PersonResponseDto> getAllPersons() {
        return Flux.fromIterable(personRepository.findAll())
                .map(person -> new PersonResponseDto(person.getId(), person.getName(), person.getGender(), person.getAge(),
                        person.getAddress(), person.getPhone()));
    }

    @Override
    public Mono<PersonResponseDto> getPersonById(Long id) {
        return Mono.justOrEmpty(personRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Person not found")))
                .map(person -> new PersonResponseDto(person.getId(), person.getName(), person.getGender(), person.getAge(),
                        person.getAddress(), person.getPhone()));
    }

    @Override
    public Mono<PersonResponseDto> updatePerson(Long id, CreatePersonRequestDto requestDto) {
        return Mono.justOrEmpty(personRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Person not found")))
                .map(person -> {
                    person.setName(requestDto.getName());
                    person.setGender(requestDto.getGender());
                    person.setAge(requestDto.getAge());
                    person.setAddress(requestDto.getAddress());
                    person.setPhone(requestDto.getPhone());
                    return personRepository.save(person);
                })
                .map(updatedPerson -> new PersonResponseDto(updatedPerson.getId(), updatedPerson.getName(), updatedPerson.getGender(),
                        updatedPerson.getAge(), updatedPerson.getAddress(), updatedPerson.getPhone()));
    }

    @Override
    public Mono<Void> deletePerson(Long id) {
        return Mono.justOrEmpty(personRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomException("Person not found")))
                .doOnNext(personRepository::delete)
                .then();
    }
}
