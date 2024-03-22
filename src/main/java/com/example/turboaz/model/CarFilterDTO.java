package com.example.turboaz.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarFilterDTO {
    @NotBlank(message = "Brand cannot be blank")
    private String brand;
    @NotBlank(message = "Model cannot be lank")
    private String model;
    @Min(value = 1924, message = "Min year must be 1924 or over")
    private Integer minYear;
    @Positive(message = "Max year must be a positive number")
    private Integer maxYear;
    @Min(value = 0, message = "Min price must be 0 or over")
    private Double minPrice;
    @Positive(message = "Max year must be a positive number")
    private Double maxPrice;
}