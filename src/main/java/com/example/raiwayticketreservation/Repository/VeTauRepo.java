package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.VeTau;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VeTauRepo extends JpaRepository<VeTau, Long> {

    @Query(value = "SELECT id FROM railwayticketreservationdb.vetau\n" +
            "WHERE ten_hanh_khach = ? AND so_giay_to = ? " +
            "AND ma_khach_dat = ? AND ma_hanh_trinh = ?", nativeQuery = true)
    public Long getIDVeTau(String tenHanhKhach, String soGiayTo, Long maKhachDat, Long maHanhTrinh);
    @Query(value = "SELECT * FROM railwayticketreservationdb.vetau WHERE ma_ve = ?", nativeQuery = true)
    public VeTau getVeTheoMaVe(String maVe);
}
