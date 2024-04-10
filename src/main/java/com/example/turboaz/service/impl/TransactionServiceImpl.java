package com.example.turboaz.service.impl;

import com.example.turboaz.dao.entity.TransactionEntity;
import com.example.turboaz.dao.repository.TransactionRepository;
import com.example.turboaz.enums.TransactionStatus;
import com.example.turboaz.exception.NotFoundException;
import com.example.turboaz.mapper.TransactionMapper;
import com.example.turboaz.model.TransactionCancellationDTO;
import com.example.turboaz.model.TransactionDTO;
import com.example.turboaz.model.TransactionStatusUpdateDTO;
import com.example.turboaz.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionDTO initiate(TransactionDTO transactionDTO){
        log.info("Starting transaction for car ID: {}", transactionDTO.getCarId());
        TransactionEntity transactionEntity = transactionMapper.mapToEntity(transactionDTO);
        transactionEntity = transactionRepository.save(transactionEntity);
        TransactionDTO initiatedTransaction = transactionMapper.mapToDTO(transactionEntity);
        log.info("Transaction started successfully for car ID: {}", transactionDTO.getCarId());
        return initiatedTransaction;
    }

    @Override
    @Transactional
    public void updateStatus(TransactionStatusUpdateDTO updatedTransactionStatus) {
        Long transactionId = updatedTransactionStatus.getTransactionId();
        TransactionStatus newStatus = updatedTransactionStatus.getNewStatus();
        log.info("Updating transaction status for transaction ID: {}", transactionId);
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found with ID: " + transactionId));
        transactionEntity.setStatus(newStatus);
        transactionRepository.save(transactionEntity);
        log.info("Transaction status updated successfully for transaction ID: {}", transactionId);
    }

//    public void update(Long id, TransactionDTO transactionDTO){
//        var exist = transactionRepository.findById(id).orElseThrow(
//                () -> new NotFoundException("Transaction not found with ID: \" + transactionId")
//        );
//        transactionRepository.save(transactionMapper.mapToUpdateEntity(exist, transactionDTO));
//    }

    @Override
    @Transactional
    public void cancel(TransactionCancellationDTO cancellationDTO) {
        Long transactionId = cancellationDTO.getTransactionId();
        log.info("Cancelling transaction for transaction ID: {}", transactionId);
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found with ID: " + transactionId));
        transactionEntity.setStatus(TransactionStatus.CANCELLED);
        transactionRepository.save(transactionEntity);
        log.info("Transaction cancelled successfully for transaction ID: {}", transactionId);
    }

    @Override
    public Page<TransactionDTO> getList(Long carId, Pageable pageable){
        log.info("Fetching transaction history for car ID: {}", carId);
        Page<TransactionEntity> transactionPage = transactionRepository.findByCarEntityCarId(carId, pageable);
        log.info("Transaction history fetched successfully for car ID: {}", carId);
        return transactionPage.map(transactionMapper::mapToDTO);
    }

}