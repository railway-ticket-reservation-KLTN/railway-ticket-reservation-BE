package com.example.raiwayticketreservation.service;

import java.util.Map;

public interface EmailService {
    public void guiMaXacThucMessage(String to, String subject, Map<String, Object> variable);

    public void guiVeTauMessage(String to, String subject, Map<String, Object> variable);

    public void thanhToanVeKhongThanhCongMessage(String to, String subject, Map<String, Object> variable);
}
