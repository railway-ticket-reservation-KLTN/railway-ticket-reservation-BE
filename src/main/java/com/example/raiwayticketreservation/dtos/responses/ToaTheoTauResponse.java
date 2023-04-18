package com.example.raiwayticketreservation.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToaTheoTauResponse {
    private Long maHanhTrinh;
    private String tenTau;
    private String soToa;
    private String tenToa;
    private String moTaToa;
    private int soLuongGhe;
}
