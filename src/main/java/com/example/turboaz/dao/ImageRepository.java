package com.example.turboaz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer>, JpaSpecificationExecutor<ImageEntity> {

    Page<ImageEntity> findByCarEntityCarId(Long carId, Pageable pageable);

}