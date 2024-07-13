package com.account_movements.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementResponseDto {
    private Long id;
    private Date date;
    private String movementType;
    private Double value;
    private Double balance;
    private Long accountId;
}
