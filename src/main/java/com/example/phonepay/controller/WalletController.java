package com.example.phonepay.controller;

import com.example.phonepay.dto.ApiResponse;
import com.example.phonepay.dto.CreateWalletRequest;
import com.example.phonepay.model.Wallet;
import com.example.phonepay.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Wallet>> createWallet(@Valid @RequestBody CreateWalletRequest request) {
        Wallet created = paymentService.createWallet(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Wallet created successfully", created));
    }

    @GetMapping("/{userName}")
    public ResponseEntity<ApiResponse<Wallet>> getWallet(@PathVariable String userName) {
        Wallet wallet = paymentService.getWallet(userName);
        return ResponseEntity.ok(new ApiResponse<>(true, "Wallet fetched", wallet));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Wallet>>> getAllWallets() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Wallet list fetched", paymentService.getAllWallets()));
    }

    // Backward-compatible alias for existing clients.
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Wallet>> createWalletAlias(@Valid @RequestBody CreateWalletRequest request) {
        return createWallet(request);
    }
}
