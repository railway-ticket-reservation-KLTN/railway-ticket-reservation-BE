package com.example.raiwayticketreservation.dtos.requests;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
public class TrangThaiGheRequest {
    private String gaDi;
    private String gaDen;
    private Long maGhe;
    private Long maVe;
    private String ngayDi;
    private Time gioDi;
    private Time gioDen;
    private int soToa;
    private String tenTau;
    private String trangThai;
}
