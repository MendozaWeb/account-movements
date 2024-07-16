package com.client_person.dto.request;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateClientRequestDto {
    private String clientId;
    private String password;
    private boolean status;
    private Long personId;
}
