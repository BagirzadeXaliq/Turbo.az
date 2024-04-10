package com.example.turboaz.controller;

import com.example.turboaz.model.TransactionCancellationDTO;
import com.example.turboaz.model.TransactionDTO;
import com.example.turboaz.model.TransactionStatusUpdateDTO;
import com.example.turboaz.service.TransactionService;
import jakarta.validation.Valid;
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
    public TransactionDTO initiate(@Valid @RequestBody TransactionDTO transactionDTO) {
        return transactionService.initiate(transactionDTO);
    }

    @PutMapping ("/update-status")
    public void updateStatus(@Valid @RequestBody TransactionStatusUpdateDTO updatedTransactionStatus) {
        transactionService.updateStatus(updatedTransactionStatus);
    }

    @PostMapping("/cancel")
    public void cancel(@Valid @RequestBody TransactionCancellationDTO cancellationDTO) {
        transactionService.cancel(cancellationDTO);
    }

    @GetMapping("/{userId}")
    public Page<TransactionDTO> getList(@PathVariable Long userId, Pageable pageable) {
        return transactionService.getList(userId, pageable);
    }
}