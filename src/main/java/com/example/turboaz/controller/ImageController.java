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
    public ImageDTO upload(@Valid @RequestBody ImageDTO newImage) {
        return imageService.upload(newImage);
    }

    @DeleteMapping("/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer imageId) {
        imageService.delete(imageId);
    }

    @PutMapping("/{imageId}")
    public void update(@PathVariable Integer imageId,@Valid @RequestBody ImageDTO updatedImage) {
        imageService.update(imageId, updatedImage);
    }

    @GetMapping("/{carId}")
    public Page<ImageDTO> getList(@PathVariable Long carId, Pageable pageable) {
        return imageService.getList(carId, pageable);
    }
}