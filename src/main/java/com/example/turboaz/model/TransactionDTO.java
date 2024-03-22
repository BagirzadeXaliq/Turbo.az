package com.example.turboaz.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {
    @NotNull(message = "BuyerUserId cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long buyerUserId;
    @NotNull(message = "SellerUserId cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long sellerUserId;
    @NotNull(message = "CarId cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long carId;
}