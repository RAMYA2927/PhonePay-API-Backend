package com.example.phonepay.service;

import com.example.phonepay.dto.LoginRequest;
import com.example.phonepay.dto.RegisterRequest;
import com.example.phonepay.dto.UpdatePinRequest;
import com.example.phonepay.exception.BadRequestException;
import com.example.phonepay.exception.NotFoundException;
import com.example.phonepay.model.Wallet;
import com.example.phonepay.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AuthService {

    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Wallet register(RegisterRequest request) {
        String upi = request.getUpi().trim();
        if (walletRepository.existsByUserName(upi)) {
            throw new BadRequestException("User already registered with UPI id: " + upi);
        }

        if (request.getInitialBalance() == null || request.getInitialBalance().signum() < 0) {
            throw new BadRequestException("Initial balance must be zero or positive");
        }

        Wallet wallet = new Wallet();
        wallet.setUserName(upi);
        wallet.setFullName(request.getName().trim());
        wallet.setPhoneNumber(request.getPhone().trim());
        wallet.setBalance(request.getInitialBalance().setScale(2, RoundingMode.HALF_UP));
        wallet.setPinHash(passwordEncoder.encode(request.getPin()));

        return walletRepository.save(wallet);
    }

    public Wallet login(LoginRequest request) {
        String upi = request.getUpi().trim();
        Wallet wallet = walletRepository.findByUserName(upi)
                .orElseThrow(() -> new NotFoundException("No wallet found for UPI id: " + upi));

        String storedPin = wallet.getPinHash();
        if (storedPin == null || !passwordEncoder.matches(request.getPin(), storedPin)) {
            throw new BadRequestException("Invalid credentials");
        }

        return wallet;
    }

    public Wallet updatePin(UpdatePinRequest request) {
        String upi = request.getUpi().trim();
        Wallet wallet = walletRepository.findByUserName(upi)
                .orElseThrow(() -> new NotFoundException("No wallet found for UPI id: " + upi));

        if (wallet.getPinHash() == null || !passwordEncoder.matches(request.getOldPin(), wallet.getPinHash())) {
            throw new BadRequestException("Invalid current PIN");
        }

        wallet.setPinHash(passwordEncoder.encode(request.getNewPin()));
        return walletRepository.save(wallet);
    }
}
