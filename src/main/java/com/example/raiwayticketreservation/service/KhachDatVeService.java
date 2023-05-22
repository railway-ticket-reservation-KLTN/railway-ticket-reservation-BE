package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.KhachDatVe;

public interface KhachDatVeService {
    public KhachDatVe themKhachDat(KhachDatVe khachDatVe);
    public Long getIDKhachDat(KhachDatVe khachDatVe);

    public KhachDatVe getKhachDatVeTheoID(Long id);
}
