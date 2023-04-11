package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface HanhTrinhRepo extends JpaRepository<HanhTrinh, Long> {
    @Query(value = "SELECT * FROM railwayticketdb.hanhtrinh " +
            "WHERE ga_di = ? AND ga_den = ? AND ngay_di = ?",
            nativeQuery = true)
    public HanhTrinh getHanhTrinhByGaDiGaDenNgayDiNgayDen(String gaDi, String gaDen, String ngayDi);
}
