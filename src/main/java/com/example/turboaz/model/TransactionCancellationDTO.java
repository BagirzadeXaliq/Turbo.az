package com.example.turboaz.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionCancellationDTO {
    @NotNull(message = "TransactionId cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long transactionId;
}