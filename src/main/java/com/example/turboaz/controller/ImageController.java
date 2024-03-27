package com.example.turboaz.controller;

import com.example.turboaz.model.ImageDTO;
import com.example.turboaz.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadImage(@Valid @RequestBody ImageDTO newImage) {
        imageService.uploadImage(newImage);
    }

    @DeleteMapping("/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Integer imageId) {
        imageService.deleteImage(imageId);
    }

    @PutMapping("/{imageId}")
    public void updateImage(@PathVariable Integer imageId,@Valid @RequestBody ImageDTO updatedImage) {
        imageService.updateImage(imageId, updatedImage);
    }

    @GetMapping("/list/{carId}")
    public Page<ImageDTO> listImages(@PathVariable Long carId, Pageable pageable) {
        return imageService.listImages(carId, pageable);
    }
}