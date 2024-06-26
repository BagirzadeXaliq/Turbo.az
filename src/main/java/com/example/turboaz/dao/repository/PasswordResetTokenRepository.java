package com.example.turboaz.dao.repository;

import com.example.turboaz.dao.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Date;
import java.util.List;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long>, JpaSpecificationExecutor<PasswordResetTokenEntity> {

    List<PasswordResetTokenEntity> findByExpiryDateBefore(Date date);

}