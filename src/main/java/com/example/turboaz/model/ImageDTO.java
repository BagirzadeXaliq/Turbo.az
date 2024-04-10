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
public class ImageDTO {
    @NotNull(message = "UserId cannot be null")
    @Positive(message = "ID must be a positive number")
    private Long carId;
    @NotBlank(message = "ImageUrl cannot be blank")
    private String imageUrl;
}