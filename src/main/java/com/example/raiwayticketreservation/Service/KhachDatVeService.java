package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.KhachDatVe;

public interface KhachDatVeService {
    public KhachDatVe themKhachDat(KhachDatVe khachDatVe);
    public Long getIDKhachDat(KhachDatVe khachDatVe);

    public KhachDatVe getKhachDatVeTheoID(Long id);
}
