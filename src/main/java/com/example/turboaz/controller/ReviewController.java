package com.example.turboaz.controller;

import com.example.turboaz.model.ReviewDTO;
import com.example.turboaz.model.ReviewFilterDTO;
import com.example.turboaz.service.ReviewService;
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
    public void createReview(@RequestBody ReviewDTO newReview) {
        reviewService.createReview(newReview);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @PutMapping("/{reviewId}")
    public void updateReview(@PathVariable Integer reviewId, @RequestBody ReviewDTO updatedReview) {
        reviewService.updateReview(reviewId, updatedReview);
    }

    @GetMapping("/list/{carId}")
    public Page<ReviewDTO> listReviews(@PathVariable Integer carId, ReviewFilterDTO reviewFilterDto, Pageable pageable) {
        return reviewService.listReviews(carId, reviewFilterDto, pageable);
    }
}