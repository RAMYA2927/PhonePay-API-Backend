package com.example.phonepay.controller;

import com.example.phonepay.dto.ApiResponse;
import com.example.phonepay.dto.TransferRequest;
import com.example.phonepay.model.Transaction;
import com.example.phonepay.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/payments", "/api/payment"})
@Tag(name = "Payment Management", description = "APIs for money transfer and transaction history")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money", description = "Transfers amount from sender wallet to receiver wallet")
    public ResponseEntity<ApiResponse<Transaction>> transfer(@Valid @RequestBody TransferRequest request) {
        Transaction transaction = paymentService.transfer(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Transaction successful", transaction));
    }

    @GetMapping("/history")
    @Operation(summary = "Get transaction history", description = "Fetches all transactions or transactions for a user")
    public ResponseEntity<ApiResponse<List<Transaction>>> getHistory(
            @RequestParam(name = "userName", required = false) String userName) {
        List<Transaction> history = paymentService.getHistory(userName);
        return ResponseEntity.ok(new ApiResponse<>(true, "Transaction history fetched", history));
    }

    // Backward-compatible alias for existing clients.
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Transaction>> sendMoneyAlias(@Valid @RequestBody TransferRequest request) {
        return transfer(request);
    }
}
