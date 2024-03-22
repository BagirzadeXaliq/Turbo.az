package com.example.turboaz.mapper;

import com.example.turboaz.dao.RoleEntity;
import com.example.turboaz.dao.UserEntity;
import com.example.turboaz.model.UserDTO;
import com.example.turboaz.model.UserRegisterRequestDTO;
import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    UserEntity mapRegisterRequestDtoToEntity(UserRegisterRequestDTO userRegisterRequestDTO);

    @AfterMapping
    default void mapRoles(UserRegisterRequestDTO userRegisterRequestDTO, @MappingTarget UserEntity userEntity) {
        Set<RoleEntity> roleEntities = userRegisterRequestDTO.getRoles().stream()
                .map(roleDTO -> {
                    RoleEntity roleEntity = new RoleEntity();
                    roleEntity.setRoleId(roleDTO.getId());
                    return roleEntity;
                })
                .collect(Collectors.toSet());
        userEntity.setRoles(roleEntities);
    }

    UserDTO toUserDTO(UserEntity userEntity);
}