package com.example.turboaz.mapper;

import com.example.turboaz.dao.entity.RoleEntity;
import com.example.turboaz.model.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

    RoleEntity mapToEntity(RoleDTO roleDTO);

    RoleDTO mapToDTO(RoleEntity roleEntity);

}