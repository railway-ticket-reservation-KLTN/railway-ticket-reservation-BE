package com.example.raiwayticketreservation.dtos.requests;

import com.example.raiwayticketreservation.Entity.KhachDatVe;
import com.example.raiwayticketreservation.Entity.VeTau;
import lombok.Data;

import java.util.Set;

@Data
public class XacThucRequest {
    private KhachDatVe khachDatVe;
    private String maXacThuc;
    private Set<VeTauRequest> veTaus;
}
