package com.example.turboaz.service;

import com.example.turboaz.dao.entity.TransactionEntity;
import com.example.turboaz.dao.repository.TransactionRepository;
import com.example.turboaz.enums.TransactionStatus;
import com.example.turboaz.exception.TransactionNotFoundException;
import com.example.turboaz.mapper.TransactionMapper;
import com.example.turboaz.model.TransactionCancellationDTO;
import com.example.turboaz.model.TransactionDTO;
import com.example.turboaz.model.TransactionStatusUpdateDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public void initiateTransaction(TransactionDTO transactionDTO){
        log.info("Starting transaction for car ID: {}", transactionDTO.getCarId());
        TransactionEntity transactionEntity = transactionMapper.mapToEntity(transactionDTO);
        transactionRepository.save(transactionEntity);
        log.info("Transaction started successfully for car ID: {}", transactionDTO.getCarId());
    }

    @Transactional
    public void updateTransactionStatus(TransactionStatusUpdateDTO updatedTransactionStatus) {
        Long transactionId = updatedTransactionStatus.getTransactionId();
        TransactionStatus newStatus = updatedTransactionStatus.getNewStatus();
        log.info("Updating transaction status for transaction ID: {}", transactionId);
        try {
            TransactionEntity transactionEntity = transactionRepository.findById(transactionId)
                    .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + transactionId));
            transactionEntity.setStatus(newStatus);
            transactionRepository.save(transactionEntity);
            log.info("Transaction status updated successfully for transaction ID: {}", transactionId);
        } catch (TransactionNotFoundException e) {
            log.error("Transaction not found with ID: {}", transactionId);
            throw e;
        } catch (Exception e) {
            log.error("Error updating transaction status for transaction ID: {}", transactionId, e);
        }
    }

    @Transactional
    public void cancelTransaction(TransactionCancellationDTO cancellationDTO) {
        Long transactionId = cancellationDTO.getTransactionId();
        log.info("Cancelling transaction for transaction ID: {}", transactionId);
        try {
            TransactionEntity transactionEntity = transactionRepository.findById(transactionId)
                    .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + transactionId));
            transactionEntity.setStatus(TransactionStatus.CANCELLED);
            transactionRepository.save(transactionEntity);
            log.info("Transaction cancelled successfully for transaction ID: {}", transactionId);
        } catch (TransactionNotFoundException e) {
            log.error("Transaction not found with ID: {}", transactionId);
            throw e;
        } catch (Exception e) {
            log.error("Error cancelling transaction for transaction ID: {}", transactionId, e);
        }
    }

    public Page<TransactionDTO> viewTransactionHistory(Long carId, Pageable pageable){
        log.info("Fetching transaction history for car ID: {}", carId);
        Page<TransactionEntity> transactionPage = transactionRepository.findByCarEntityCarId(carId, pageable);
        log.info("Transaction history fetched successfully for car ID: {}", carId);
        return transactionPage.map(transactionMapper::mapToDTO);
    }

}