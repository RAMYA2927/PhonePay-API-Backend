package com.example.phonepay.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // primary key

    @Column(unique = true, nullable = false)
    private String userName;        // wallet owner

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;     // current balance

    // Optional: add more fields like email, phone number
    @Column
    private String email;

    @Column
    private String phoneNumber;

    // Default constructor required by JPA
    public Wallet() {}

    // Constructor with fields
    public Wallet(String userName, BigDecimal balance, String email, String phoneNumber) {
        this.userName = userName;
        this.balance = balance;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
