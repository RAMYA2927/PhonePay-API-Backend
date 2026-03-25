package com.example.phonepay.controller;

import com.example.phonepay.dto.ApiResponse;
import com.example.phonepay.dto.LoginRequest;
import com.example.phonepay.dto.RegisterRequest;
import com.example.phonepay.dto.UpdatePinRequest;
import com.example.phonepay.model.Wallet;
import com.example.phonepay.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Register and login endpoints for Wallet users")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Creates a wallet account with a PIN")
    public ResponseEntity<ApiResponse<Wallet>> register(@Valid @RequestBody RegisterRequest request) {
        Wallet wallet = authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registration successful", wallet));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Validates pin for the provided UPI id")
    public ResponseEntity<ApiResponse<Wallet>> login(@Valid @RequestBody LoginRequest request) {
        Wallet wallet = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", wallet));
    }

    @PostMapping("/update-pin")
    @Operation(summary = "Update PIN", description = "Updates the wallet PIN after verifying the current PIN")
    public ResponseEntity<ApiResponse<Wallet>> updatePin(@Valid @RequestBody UpdatePinRequest request) {
        Wallet wallet = authService.updatePin(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "PIN updated successfully", wallet));
    }
}
