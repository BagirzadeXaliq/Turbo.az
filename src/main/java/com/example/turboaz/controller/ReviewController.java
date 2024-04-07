package com.example.turboaz.controller;

import com.example.turboaz.model.ReviewDTO;
import com.example.turboaz.model.ReviewFilterDTO;
import com.example.turboaz.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ReviewDTO newReview) {
        reviewService.create(newReview);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer reviewId) {
        reviewService.delete(reviewId);
    }

    @PutMapping("/{reviewId}")
    public void update(@PathVariable Integer reviewId, @Valid @RequestBody ReviewDTO updatedReview) {
        reviewService.update(reviewId, updatedReview);
    }

    @GetMapping("/{carId}")
    public Page<ReviewDTO> getList(@PathVariable Integer carId,@Valid ReviewFilterDTO reviewFilterDto, Pageable pageable) {
        return reviewService.getList(carId, reviewFilterDto, pageable);
    }
}