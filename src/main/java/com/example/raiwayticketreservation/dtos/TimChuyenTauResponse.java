package com.example.raiwayticketreservation.dtos;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Entity.Tau;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TimChuyenTauResponse {
    private Long id;
    private HanhTrinh hanhTrinh;
    private Set<Tau> taus;
}
