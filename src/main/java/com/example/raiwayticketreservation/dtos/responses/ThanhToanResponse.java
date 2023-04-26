package com.example.raiwayticketreservation.dtos.responses;

import com.example.raiwayticketreservation.Entity.VeTau;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
@Data
@Builder
public class ThanhToanResponse {
    private Set<VeTau> veTauSet;
    private String maDatCho;
    private String maDatVe;
}
