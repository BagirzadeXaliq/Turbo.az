package com.example.turboaz.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDTO {
    @NotNull(message = "UserId cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long userId;
    @NotNull(message = "CarId cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long carId;
    @NotBlank(message = "Review cannot be blank")
    private String review;
}