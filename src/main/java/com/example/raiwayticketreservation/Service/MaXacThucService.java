package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.KhachDatVe;
import com.example.raiwayticketreservation.Entity.MaXacThuc;

public interface MaXacThucService {
    public MaXacThuc timMaXacThucTheoToken(String token);

    public void themMaXacThuc(KhachDatVe khachDatVe, String maXacThuc);

}
