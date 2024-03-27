package com.example.turboaz.dao.repository;

import com.example.turboaz.dao.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, JpaSpecificationExecutor<TransactionEntity> {

    Page<TransactionEntity> findByCarEntityCarId(Long carId, Pageable pageable);

}