package com.example.raiwayticketreservation.dtos;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import lombok.Data;

@Data
public class TrangThaiGheRequest {
    private String gaDi;
    private String gaDen;
    private Long maGhe;
    private Long maVe;
    private String ngayDi;
    private String gioDi;
    private String gioDen;
    private int soToa;
    private String tenTau;
    private String trangThai;
}
