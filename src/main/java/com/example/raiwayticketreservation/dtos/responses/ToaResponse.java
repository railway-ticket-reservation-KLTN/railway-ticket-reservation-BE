package com.example.raiwayticketreservation.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToaResponse {
    public Long maToa;
    public String soToa;
    public String tenTau;
    public String moTaToa;
    public int soLuongGhe;
    public String tenToa;
}
