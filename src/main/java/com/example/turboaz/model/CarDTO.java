package com.example.turboaz.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDTO {
    @NotBlank(message = "Brand cannot be blank")
    private String brand;
    @NotBlank(message = "Model cannot be blank")
    private String model;
    @Min(value = 1924, message = "Year must be 1924 or over")
    @Max(value = 2024, message = "Year must be 2024 or under")
    private Integer year;
    @Positive(message = "Price must be a positive number")
    private Double price;
}