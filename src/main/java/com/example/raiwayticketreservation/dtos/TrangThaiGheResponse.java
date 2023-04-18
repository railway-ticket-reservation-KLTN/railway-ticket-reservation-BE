package com.example.raiwayticketreservation.dtos;

import lombok.Data;

@Data
public class TrangThaiGheResponse {
    private Long id;
    private String gaDen;
    private String gaDi;
    private String trangThai;
    private Long maGhe;
    private String ngayDi;
    private String gioDi;
    private String gioDen;
    private String tenTau;
    private int soToa;
}
