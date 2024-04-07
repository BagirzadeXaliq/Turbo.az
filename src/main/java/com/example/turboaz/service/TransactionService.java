package com.example.turboaz.service;

import com.example.turboaz.model.TransactionCancellationDTO;
import com.example.turboaz.model.TransactionDTO;
import com.example.turboaz.model.TransactionStatusUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    void initiate(TransactionDTO transactionDTO);

    void updateStatus(TransactionStatusUpdateDTO updatedTransactionStatus);

    void cancel(TransactionCancellationDTO cancellationDTO);

    Page<TransactionDTO> getList(Long carId, Pageable pageable);
}
