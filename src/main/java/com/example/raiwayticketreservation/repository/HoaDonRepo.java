package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.HoaDon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HoaDonRepo extends JpaRepository<HoaDon, Long> {
    @Query(value = "SELECT id FROM railwayticketreservationdb.hoadon\n" +
            "WHERE ma_dat_cho = ? OR ma_dat_ve = ?", nativeQuery = true)
    public Long getIDHoaDon(String maDatCho, String maDatVe);
    @Query(value = "SELECT * FROM railwayticketreservationdb.hoadon\n" +
            "WHERE ma_dat_ve = ?", nativeQuery = true)
    public HoaDon getHDByMaDatVe(String maDatVe);

    @Modifying
    @Transactional
    @Query(value = "UPDATE railwayticketreservationdb.hoadon \n" +
            "SET ma_dat_ve = ?, tinh_trang = ?\n" +
            "WHERE ma_dat_cho = ?", nativeQuery = true)
    public void capNhatHoaDonTheoMaDatCho(String maDatVe, String tinhTrang, String maDatCho);
}
