package com.example.turboaz.dao;

import com.example.turboaz.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findUserByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<List<UserEntity>> findByStatus(UserStatus status);
}