package com.example.turboaz.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {
    @NotNull(message = "Code cannot be null")
    @PositiveOrZero(message = "Code must be a positive number or zero")
    private Integer code;
    @NotNull(message = "Message cannot be null")
    private String message;
}