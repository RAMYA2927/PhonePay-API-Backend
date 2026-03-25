package com.example.phonepay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "wallets")
public class Wallet {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;

    private BigDecimal balance;

    private String fullName;

    @JsonIgnore
    private String pinHash;

    private String email;

    private String phoneNumber;

    // Default constructor required by JPA
    public Wallet() {}

    // Constructor with fields
    public Wallet(String userName, BigDecimal balance, String email, String phoneNumber) {
        this.userName = userName;
        this.balance = balance;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = null;
        this.pinHash = null;
    }

    // Getters and Setters
    public String getId() { return id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPinHash() { return pinHash; }
    public void setPinHash(String pinHash) { this.pinHash = pinHash; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
