package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.util.Date;

public interface HanhTrinhRepo extends JpaRepository<HanhTrinh, Long> {
    @Query(value = "SELECT * FROM railwayticketreservationdb.hanhtrinh " +
            "WHERE ga_di = ? AND ga_den = ? AND ngay_di = ?",
            nativeQuery = true)
    public HanhTrinh getHanhTrinhByGaDiGaDenNgayDiNgayDen(String gaDi, String gaDen, String ngayDi);
    @Query(value = "SELECT id from railwayticketreservationdb.hanhtrinh \n" +
            "WHERE ga_di = ? AND ga_den = ? AND ngay_di = ? " +
            "AND ngay_den = ? AND gio_di = ? AND gio_den = ?", nativeQuery = true)
    public Long getIdHanhTrinh(String gaDi, String gaDen, String ngayDi, String ngayDen, String gioDi, String gioDen);

    @Query(value = "SELECT * FROM railwayticketreservationdb.hanhtrinh WHERE id = ?", nativeQuery = true)
    public HanhTrinh getHanhTrinhByHanhTrinhID(Long hanhTrinhID);
}
