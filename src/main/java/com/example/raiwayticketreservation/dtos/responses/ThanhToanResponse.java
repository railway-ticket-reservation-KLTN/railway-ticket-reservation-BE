package com.example.raiwayticketreservation.dtos.responses;

import com.example.raiwayticketreservation.entities.VeTau;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
@Data
@Builder
public class ThanhToanResponse {
    private Set<VeTau> veTaus;
    private String maDatCho;
    private String maDatVe;
}
