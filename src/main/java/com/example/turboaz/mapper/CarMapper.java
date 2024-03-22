package com.example.turboaz.mapper;

import com.example.turboaz.dao.CarEntity;
import com.example.turboaz.model.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CarMapper {

    CarEntity mapToEntity(CarDTO carDTO);

    CarDTO mapToDTO(CarEntity carEntity);

}