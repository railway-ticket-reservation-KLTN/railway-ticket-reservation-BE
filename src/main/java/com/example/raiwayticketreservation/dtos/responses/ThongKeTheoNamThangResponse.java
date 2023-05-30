package com.example.raiwayticketreservation.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThongKeTheoNamThangResponse {
    private int nam;
    private int thang;
    private int soVe;
    private double doanhThu;
}
