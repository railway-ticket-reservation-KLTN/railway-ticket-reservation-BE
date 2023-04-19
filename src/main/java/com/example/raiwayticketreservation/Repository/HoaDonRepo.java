package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HoaDonRepo extends JpaRepository<HoaDon, Long> {
    @Query(value = "SELECT id FROM railwayticketreservationdb.hoadon\n" +
            "WHERE ma_dat_cho = ? OR ma_dat_ve = ?", nativeQuery = true)
    public Long getIDHoaDon(String maDatCho, String maDatVe);
    @Query(value = "SELECT * FROM railwayticketreservationdb.hoadon\n" +
            "WHERE ma_dat_ve = ?", nativeQuery = true)
    public HoaDon getHDByMaDatVe(String maDatVe);
}
