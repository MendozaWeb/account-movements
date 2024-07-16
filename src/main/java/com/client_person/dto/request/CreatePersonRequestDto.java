package com.client_person.dto.request;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePersonRequestDto {
    private String name;
    private String gender;
    private int age;
    private String address;
    private String phone;
}
