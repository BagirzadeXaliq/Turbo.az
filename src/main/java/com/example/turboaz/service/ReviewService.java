package com.example.turboaz.service;

import com.example.turboaz.dao.ReviewEntity;
import com.example.turboaz.dao.ReviewRepository;
import com.example.turboaz.exception.ReviewNotFoundException;
import com.example.turboaz.mapper.ReviewMapper;
import com.example.turboaz.model.ReviewDTO;
import com.example.turboaz.model.ReviewFilterDTO;
import com.example.turboaz.service.specification.ReviewSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public void createReview(ReviewDTO newReview) {
        log.info("Creating new review: {}", newReview);
        ReviewEntity reviewEntity = reviewMapper.mapToEntity(newReview);
        reviewRepository.save(reviewEntity);
        log.info("Review created successfully: {}", newReview);
    }

    public void deleteReview(Integer reviewId) {
        log.info("Deleting review with ID: {}", reviewId);
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            log.info("Review deleted successfully with ID: {}", reviewId);
        } else {
            log.error("Review not found with ID: {}", reviewId);
            throw new ReviewNotFoundException("Review not found with ID: " + reviewId);
        }
    }

    public void updateReview(Integer reviewId, ReviewDTO updatedReview){
        log.info("Updating review with ID: {}", reviewId);
        if (reviewRepository.existsById(reviewId)) {
            ReviewEntity existingReview = reviewRepository.findById(reviewId).orElseThrow(
                    () -> new ReviewNotFoundException("Review not found with ID: " + reviewId));
            ReviewEntity updatedReviewEntity = reviewMapper.mapToEntity(updatedReview);
            updatedReviewEntity.setReviewId(existingReview.getReviewId());
            reviewRepository.save(updatedReviewEntity);
            log.info("Review updated successfully with ID: {}", reviewId);
        } else {
            log.error("Review not found with ID: {}", reviewId);
            throw new ReviewNotFoundException("Review not found with ID: " + reviewId);
        }
    }

    public Page<ReviewDTO> listReviews(Integer carId, ReviewFilterDTO reviewFilterDto, Pageable pageable) {
        log.info("Fetching reviews for car ID: {}", carId);
        Specification<ReviewEntity> specifications = new ReviewSpecification().getReviewSpecification(carId, reviewFilterDto);
        Page<ReviewEntity> reviewPage = reviewRepository.findAll(specifications, pageable);
        log.info("Reviews fetched successfully for car ID: {}", carId);
        return reviewPage.map(reviewMapper::mapToDTO);
    }

}