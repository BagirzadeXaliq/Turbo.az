package com.example.turboaz.service.impl;

import com.example.turboaz.dao.entity.ReviewEntity;
import com.example.turboaz.dao.repository.ReviewRepository;
import com.example.turboaz.exception.NotFoundException;
import com.example.turboaz.mapper.ReviewMapper;
import com.example.turboaz.model.ReviewDTO;
import com.example.turboaz.model.ReviewFilterDTO;
import com.example.turboaz.service.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDTO create(ReviewDTO newReview) {
        log.info("Creating new review: {}", newReview);
        ReviewEntity reviewEntity = reviewMapper.mapToEntity(newReview);
        reviewEntity = reviewRepository.save(reviewEntity);
        ReviewDTO createdReview = reviewMapper.mapToDTO(reviewEntity);
        log.info("Review created successfully: {}", createdReview);
        return createdReview;
    }

    @Override
    public void delete(Integer reviewId) {
        log.info("Deleting review with ID: {}", reviewId);
        reviewRepository.deleteById(reviewId);
        log.info("Review deleted successfully with ID: {}", reviewId);
    }

    @Override
    public void update(Integer reviewId, ReviewDTO updatedReview){
        log.info("Updating review with ID: {}", reviewId);
        ReviewEntity existingReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("Review not found with ID: " + reviewId));
        ReviewEntity updatedReviewEntity = reviewMapper.mapToEntity(updatedReview);
        updatedReviewEntity.setReviewId(existingReview.getReviewId());
        reviewRepository.save(updatedReviewEntity);
        log.info("Review updated successfully with ID: {}", reviewId);
    }

    @Override
    public Page<ReviewDTO> getList(Integer carId, ReviewFilterDTO reviewFilterDto, Pageable pageable) {
        log.info("Fetching reviews for car ID: {}", carId);
        Specification<ReviewEntity> specifications = new ReviewSpecification().getReviewSpecification(carId, reviewFilterDto);
        Page<ReviewEntity> reviewPage = reviewRepository.findAll(specifications, pageable);
        log.info("Reviews fetched successfully for car ID: {}", carId);
        return reviewPage.map(reviewMapper::mapToDTO);
    }

}