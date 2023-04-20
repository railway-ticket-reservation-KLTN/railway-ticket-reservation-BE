package com.example.raiwayticketreservation.dtos.responses;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class TrangThaiGheResponse {
    private Long id;
    private String gaDen;
    private String gaDi;
    private String trangThai;
    private Long maGhe;
    private Date ngayDi;
    private Time gioDi;
    private Time gioDen;
    private String tenTau;
    private int soToa;
    private Timestamp thoiHanGiuGhe;
}
