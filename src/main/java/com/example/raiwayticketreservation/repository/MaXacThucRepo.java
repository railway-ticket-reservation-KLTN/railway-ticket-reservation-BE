package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.MaXacThuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaXacThucRepo extends JpaRepository<MaXacThuc, Long> {
    @Query(value = "SELECT * FROM railwayticketreservationdb.maxacthuc WHERE ma_xac_thuc = ?", nativeQuery = true)
    public MaXacThuc timMaXacThucTheoMaXT(String maXT);
}
