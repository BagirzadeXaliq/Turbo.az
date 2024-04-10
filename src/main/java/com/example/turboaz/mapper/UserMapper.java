package com.example.turboaz.mapper;

import com.example.turboaz.dao.entity.UserEntity;
import com.example.turboaz.model.UserDTO;
import com.example.turboaz.model.UserRegisterRequestDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserEntity mapRegisterRequestDtoToEntity(UserRegisterRequestDTO userRegisterRequestDTO);

    UserDTO toUserDTO(UserEntity userEntity);

}