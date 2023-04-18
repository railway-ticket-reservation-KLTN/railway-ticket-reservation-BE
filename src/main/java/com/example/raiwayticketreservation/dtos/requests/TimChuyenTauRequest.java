package com.example.raiwayticketreservation.dtos.requests;

import lombok.Data;

@Data
public class TimChuyenTauRequest {
    private String gaDi;
    private String gaDen;
    private String ngayDi;
    private String ngayVe;
    private String loaiHanhTrinh;
}
