package com.example.phonepay.controller;

import com.example.phonepay.dto.ApiResponse;
import com.example.phonepay.dto.TransferRequest;
import com.example.phonepay.model.Transaction;
import com.example.phonepay.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/payments", "/api/payment"})
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<Transaction>> transfer(@Valid @RequestBody TransferRequest request) {
        Transaction transaction = paymentService.transfer(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Transaction successful", transaction));
    }

    @GetMapping("/history")
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
