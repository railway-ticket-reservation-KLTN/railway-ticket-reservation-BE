package com.example.raiwayticketreservation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToaResponse {
    public Long id;
    public String soToa;
    public String moTaToa;
    public int soLuongGhe;
    public String ten_toa;
}
