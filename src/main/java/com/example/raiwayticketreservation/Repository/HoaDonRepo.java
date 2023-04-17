package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HoaDonRepo extends JpaRepository<HoaDon, Long> {
    @Query(value = "SELECT id FROM railwayticketreservationdb.hoadon\n" +
            "WHERE ngay_lap = ? AND ma_khach_dat = ?", nativeQuery = true)
    public Long getIDHoaDon(String ngayLap, Long maKhachDat);
}
