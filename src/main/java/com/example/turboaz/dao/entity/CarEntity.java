package com.example.turboaz.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cars")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    private String brand;
    private String model;
    private Integer year;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @OneToMany(mappedBy = "carEntity", cascade = CascadeType.ALL)
    private List<ImageEntity> images;
    @OneToMany(mappedBy = "carEntity", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;
    @OneToMany(mappedBy = "carEntity", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;
}