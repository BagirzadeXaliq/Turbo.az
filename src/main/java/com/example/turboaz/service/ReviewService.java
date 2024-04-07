package com.example.turboaz.service;

import com.example.turboaz.model.ReviewDTO;
import com.example.turboaz.model.ReviewFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    void create(ReviewDTO newReview);

    void delete(Integer reviewId);

    void update(Integer reviewId, ReviewDTO updatedReview);

    Page<ReviewDTO> getList(Integer carId, ReviewFilterDTO reviewFilterDto, Pageable pageable);

}
