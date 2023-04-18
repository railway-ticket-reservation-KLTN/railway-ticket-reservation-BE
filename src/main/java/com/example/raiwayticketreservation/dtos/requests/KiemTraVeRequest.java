package com.example.raiwayticketreservation.dtos.requests;

import lombok.Data;

@Data
public class KiemTraVeRequest {
    private String maVe;
    private String tenTau;
    private String gaDi;
    private String gaDen;
    private String ngayDi;
    private String soGiayTo;
}
