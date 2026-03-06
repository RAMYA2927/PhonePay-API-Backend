package com.example.phonepay.repository;

import com.example.phonepay.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // Find a wallet by username
    Optional<Wallet> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
