package com.example.raiwayticketreservation.dtos.requests;

import com.example.raiwayticketreservation.Entity.KhachDatVe;
import lombok.Data;

@Data
public class XacThucRequest {
    private KhachDatVe khachDatVe;
    private String maXacThuc;
}
