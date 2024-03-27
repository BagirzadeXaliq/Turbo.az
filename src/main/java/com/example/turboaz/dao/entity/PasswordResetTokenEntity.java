package com.example.turboaz.dao.entity;

import com.example.turboaz.dao.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
public class PasswordResetTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expiryDate;
    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;
}