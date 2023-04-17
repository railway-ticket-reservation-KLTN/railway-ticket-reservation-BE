package com.example.raiwayticketreservation.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VeTauRequest {
    private String tenHanhKhach;
    private String soGiayTo;
    private double donGia;
    private String loaiVe;
    private String doiTuong;
    private int trangThai;
    private String gaDi;
    private String gaDen;
    private String ngayDi;
    private String ngayDen;
    private String gioDi;
    private String gioDen;
}
