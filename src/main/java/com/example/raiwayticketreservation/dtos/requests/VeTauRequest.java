package com.example.raiwayticketreservation.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VeTauRequest {
    private String maVe;
    private String tenHanhKhach;
    private String soGiayTo;
    private double donGia;
    private String loaiVe;
    private String doiTuong;
    private int trangThai;
    private String tenTau;
    private Long maGhe;
    private int soGhe;
    private int soToa;
    private String gaDi;
    private String gaDen;
    private String ngayDi;
    private String ngayDen;
    private Time gioDi;
    private Time gioDen;
    private String tinhTrang;
}
