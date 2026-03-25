package com.example.phonepay.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdatePinRequest {

    @NotBlank
    private String upi;

    @NotBlank
    private String oldPin;

    @NotBlank
    private String newPin;

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getOldPin() {
        return oldPin;
    }

    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
}
