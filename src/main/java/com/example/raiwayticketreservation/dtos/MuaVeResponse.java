package com.example.raiwayticketreservation.dtos;

import com.example.raiwayticketreservation.Entity.KhachDatVe;
import com.example.raiwayticketreservation.Entity.VeTau;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class MuaVeResponse {
    private List<VeTau> veTaus;
    private KhachDatVe khachDatVe;
    private String hinhThucThanhToan;
    private String ngayLap;
}
