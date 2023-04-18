package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.KhachDatVe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KhachDatVeRepo extends JpaRepository<KhachDatVe, Long> {
    @Query(value = "SELECT id FROM railwayticketreservationdb.khachdatve\n" +
            "WHERE ho_ten = ? AND sdt = ? AND email = ? AND so_giay_to = ?", nativeQuery = true)
    public Long getIDKhachDatVe(String hoTen, String sdt, String email, String soGiayTo);

    @Query(value = "SELECT * FROM railwayticketreservationdb.khachdatve WHERE id = ?", nativeQuery = true)
    public KhachDatVe getKhachDatByKhachDatID(Long khachDatID);
}
