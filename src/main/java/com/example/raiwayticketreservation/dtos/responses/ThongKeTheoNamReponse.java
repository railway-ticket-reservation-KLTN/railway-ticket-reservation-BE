package com.example.raiwayticketreservation.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThongKeTheoNamReponse {
    private int nam;
    private int soVe;
    private double doanhThu;
}
