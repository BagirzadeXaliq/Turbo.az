package com.example.turboaz.dao;

import com.example.turboaz.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long buyerUserId;
    private Long sellerUserId;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarEntity carEntity;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}