package com.example.phonepay.controller;

import com.example.phonepay.dto.ApiResponse;
import com.example.phonepay.dto.CreateWalletRequest;
import com.example.phonepay.model.Wallet;
import com.example.phonepay.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@Tag(name = "Wallet Management", description = "APIs for wallet creation and retrieval")
public class WalletController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Create wallet", description = "Creates a wallet for a user with initial balance")
    public ResponseEntity<ApiResponse<Wallet>> createWallet(@Valid @RequestBody CreateWalletRequest request) {
        Wallet created = paymentService.createWallet(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Wallet created successfully", created));
    }

    @GetMapping("/{userName}")
    @Operation(summary = "Get wallet by userName", description = "Fetches wallet details for a specific user")
    public ResponseEntity<ApiResponse<Wallet>> getWallet(@PathVariable String userName) {
        Wallet wallet = paymentService.getWallet(userName);
        return ResponseEntity.ok(new ApiResponse<>(true, "Wallet fetched", wallet));
    }

    @GetMapping
    @Operation(summary = "List all wallets", description = "Fetches all wallets")
    public ResponseEntity<ApiResponse<List<Wallet>>> getAllWallets() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Wallet list fetched", paymentService.getAllWallets()));
    }

    // Backward-compatible alias for existing clients.
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Wallet>> createWalletAlias(@Valid @RequestBody CreateWalletRequest request) {
        return createWallet(request);
    }
}
