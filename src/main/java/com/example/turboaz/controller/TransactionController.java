package com.example.turboaz.controller;

import com.example.turboaz.model.TransactionCancellationDTO;
import com.example.turboaz.model.TransactionDTO;
import com.example.turboaz.model.TransactionStatusUpdateDTO;
import com.example.turboaz.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public void initiateTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.initiateTransaction(transactionDTO);
    }

    @PutMapping ("/update-status")
    public void updateTransactionStatus(@RequestBody TransactionStatusUpdateDTO updatedTransactionStatus) {
        transactionService.updateTransactionStatus(updatedTransactionStatus);
    }

    @PostMapping("/cancel")
    public void cancelTransaction(@RequestBody TransactionCancellationDTO cancellationDTO) {
        transactionService.cancelTransaction(cancellationDTO);
    }

    @GetMapping("/history/{userId}")
    public Page<TransactionDTO> viewTransactionHistory(@PathVariable Long userId, Pageable pageable) {
        return transactionService.viewTransactionHistory(userId, pageable);
    }
}