package com.example.turboaz.service.specification;

import com.example.turboaz.dao.entity.ReviewEntity;
import com.example.turboaz.model.ReviewFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {

    public Specification<ReviewEntity> getReviewSpecification(Integer carId, ReviewFilterDTO reviewFilterDto) {
        return Specification.where(reviewsBelongingToCar(carId))
                .and(withRating(reviewFilterDto.getRating()));
    }

    private Specification<ReviewEntity> reviewsBelongingToCar(Integer carId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("carId"), carId);
    }

    private Specification<ReviewEntity> withRating(Integer rating) {
        if (rating == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("rating"), rating);
    }

}