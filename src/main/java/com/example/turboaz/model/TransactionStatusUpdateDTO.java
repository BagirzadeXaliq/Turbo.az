package com.example.turboaz.model;

import com.example.turboaz.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionStatusUpdateDTO {
    @NotNull(message = "TransactionId cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long transactionId;
    @NotNull(message = "NewStatus cannot be null")
    private TransactionStatus newStatus;
}