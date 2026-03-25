package com.example.phonepay.repository;

import com.example.phonepay.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {

    // Find a wallet by username
    Optional<Wallet> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
