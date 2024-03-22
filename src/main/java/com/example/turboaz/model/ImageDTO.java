package com.example.turboaz.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9-.]+.[a-zA-Z]{2,3}(/S*)?$", message = "Invalid image URL format")
    private String imageUrl;
}