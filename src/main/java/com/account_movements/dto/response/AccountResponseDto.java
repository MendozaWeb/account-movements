package com.account_movements.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
    private Long id;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Boolean status;
}
