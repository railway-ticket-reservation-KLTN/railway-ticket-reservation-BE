package com.example.raiwayticketreservation.dtos;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import lombok.Data;

@Data
public class TrangThaiGheRequest extends TrangThaiGhe {
    private String gaDi;
    private String gaDen;
    private Long maGhe;
    private Long maVe;
    private String trangThai;
}
