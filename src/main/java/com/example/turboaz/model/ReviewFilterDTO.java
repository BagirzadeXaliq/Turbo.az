package com.example.turboaz.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewFilterDTO {
    @Min(value = 1, message = "Rating must be 1 or over")
    @Max(value = 5, message = "Rating must be 5 or under")
    private Integer rating;
}