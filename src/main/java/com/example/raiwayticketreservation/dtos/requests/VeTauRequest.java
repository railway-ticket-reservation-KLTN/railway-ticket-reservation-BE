package com.example.raiwayticketreservation.dtos.requests;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Builder
public class VeTauRequest {
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
}
