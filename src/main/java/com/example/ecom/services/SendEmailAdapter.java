package com.example.ecom.services;

public interface SendEmailAdapter {
    void sendEmailAsync(String email, String subject, String body);
}
