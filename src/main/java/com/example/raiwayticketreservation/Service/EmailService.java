package com.example.raiwayticketreservation.Service;

import java.util.List;
import java.util.Map;

public interface EmailService {
    public void guiMaXacThucMessage(String to, String subject, Map<String, Object> variable);

    public void guiVeTauMessage(String to, String subject, Map<String, Object> variable);

    public void thanhToanVeKhongThanhCongMessage(String to, String subject, Map<String, Object> variable);
}
