package com.example.raiwayticketreservation.dtos.requests;

import com.example.raiwayticketreservation.entities.KhachDatVe;
import lombok.Data;

import java.util.Set;

@Data
public class MuaVeRequest {
    private Set<VeTauRequest> veTaus;
    private KhachDatVe khachDatVe;
    private String hinhThucThanhToan;
    private String ngayLap;
}
