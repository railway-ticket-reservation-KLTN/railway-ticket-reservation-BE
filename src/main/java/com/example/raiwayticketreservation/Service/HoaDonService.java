package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.HoaDon;

public interface HoaDonService {
    public HoaDon themHoaDon(HoaDon hoaDon);

    public Long getIDHoaDon(String ngayLap, Long maKhachDat);
}
