package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.KhachDatVe;
import com.example.raiwayticketreservation.entities.MaXacThuc;

public interface MaXacThucService {
    public MaXacThuc timMaXacThucTheoToken(String token);

    public void themMaXacThuc(KhachDatVe khachDatVe, String maXacThuc);

}
