package com.example.raiwayticketreservation.Service;

public interface EmailService {
    public void sendMessage(String to, String subject, String text);
}
