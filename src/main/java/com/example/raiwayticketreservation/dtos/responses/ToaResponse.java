package com.example.raiwayticketreservation.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToaResponse {
    public Long id;
    public String soToa;
    public String tenTau;
    public String moTaToa;
    public int soLuongGhe;
    public String ten_toa;
}
