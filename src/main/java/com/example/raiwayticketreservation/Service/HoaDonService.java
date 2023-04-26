package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.HoaDon;

public interface HoaDonService {
    public HoaDon themHoaDon(HoaDon hoaDon);

    public Long getIDHoaDonTheoMaDatChoHoacMaDatVe(String maDatCho, String maDatVe);

    public HoaDon getHoaDonByMaDatVe(String maDatVe);

    public void capNhatHoaDonTheoMaDatCho(String maDatVe, String tinhTrang, String maDatCho);
}
