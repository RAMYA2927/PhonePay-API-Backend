package com.example.phonepay.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status; // e.g., SUCCESS, FAILED

    @Column(nullable = false)
    private LocalDateTime timestamp; // transaction time

    // Default constructor
    public Transaction() {}

    // Constructor with fields
    public Transaction(String sender, String receiver, BigDecimal amount, String status, LocalDateTime timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
