package com.example.turboaz.service.specification;

import com.example.turboaz.dao.entity.CarEntity;
import com.example.turboaz.model.CarFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

    public Specification<CarEntity> getCarSpecification(CarFilterDTO carFilterDto) {

        carFilterDto.setBrand("%" + carFilterDto.getBrand() + "%");
        carFilterDto.setModel("%" + carFilterDto.getModel() + "%");

        Specification<CarEntity> brandSpecification = ((root, query, criteriaBuilder) ->
                carFilterDto.getBrand() == null || carFilterDto.getBrand().isBlank() ?
                        null : criteriaBuilder.like(root.get("brand"), carFilterDto.getBrand()));

        Specification<CarEntity> modelSpecification = ((root, query, criteriaBuilder) ->
                carFilterDto.getModel() == null || carFilterDto.getModel().isBlank() ?
                        null : criteriaBuilder.like(root.get("model"), carFilterDto.getModel()));

        Specification<CarEntity> yearSpecification = ((root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("year"), carFilterDto.getMinYear(), carFilterDto.getMaxYear()));

        Specification<CarEntity> priceSpecification = ((root, query, criteriaBuilder) ->
                (carFilterDto.getMinPrice() != null && carFilterDto.getMinPrice() >= 0) &&
                        (carFilterDto.getMaxPrice() != null && carFilterDto.getMaxPrice() >= 0) ?
                        null : criteriaBuilder.between(root.get("price"), carFilterDto.getMinPrice(), carFilterDto.getMaxPrice()));

        return Specification.where(brandSpecification)
                .and(modelSpecification)
                .and(yearSpecification)
                .and(priceSpecification);

    }

}