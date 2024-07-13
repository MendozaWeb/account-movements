package com.account_movements.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovementRequestDto {
    private Date date;
    private String movementType;
    private Double value;
    private Long accountId;
}
