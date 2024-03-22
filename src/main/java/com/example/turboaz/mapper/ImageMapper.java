package com.example.turboaz.mapper;

import com.example.turboaz.dao.ImageEntity;
import com.example.turboaz.model.ImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ImageMapper {

    ImageEntity mapToEntity(ImageDTO imageDTO);

    ImageDTO mapToDTO(ImageEntity imageEntity);

}