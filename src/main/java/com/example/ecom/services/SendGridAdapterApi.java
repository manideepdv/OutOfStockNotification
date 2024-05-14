package com.example.ecom.services;

import com.example.ecom.libraries.Sendgrid;

public class SendGridAdapterApi implements SendEmailAdapter {
    @Override
    public void sendEmailAsync(String email, String subject, String body) {
        Sendgrid sendgrid = new Sendgrid();
        sendgrid.sendEmailAsync(email, subject, body);
    }
}
