package com.example.turboaz.mapper;

import com.example.turboaz.dao.entity.ReviewEntity;
import com.example.turboaz.model.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {

    ReviewEntity mapToEntity(ReviewDTO reviewDTO);

    ReviewDTO mapToDTO(ReviewEntity reviewEntity);

}