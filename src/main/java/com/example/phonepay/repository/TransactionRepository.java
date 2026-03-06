package com.example.phonepay.repository;

import com.example.phonepay.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Optional: find all transactions by sender
    List<Transaction> findBySender(String sender);

    // Optional: find all transactions by receiver
    List<Transaction> findByReceiver(String receiver);

    List<Transaction> findBySenderOrReceiverOrderByTimestampDesc(String sender, String receiver);
}
