package com.example.phonepay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class RegisterRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "upi is required")
    private String upi;

    @NotBlank(message = "phone is required")
    private String phone;

    @NotBlank(message = "pin is required")
    @Size(min = 4, max = 6, message = "pin must be 4-6 digits")
    @Pattern(regexp = "\\d+", message = "pin must be numeric")
    private String pin;

    @NotNull(message = "initialBalance is required")
    private BigDecimal initialBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
