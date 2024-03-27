package com.example.turboaz.dao.entity;

import com.example.turboaz.dao.entity.CarEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarEntity carEntity;
    private String imageUrl;
}