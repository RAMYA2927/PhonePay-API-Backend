package com.example.phonepay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "upi is required")
    private String upi;

    @NotBlank(message = "pin is required")
    @Size(min = 4, max = 6, message = "pin must be 4–6 digits")
    @Pattern(regexp = "\\d+", message = "pin must be numeric")
    private String pin;

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
