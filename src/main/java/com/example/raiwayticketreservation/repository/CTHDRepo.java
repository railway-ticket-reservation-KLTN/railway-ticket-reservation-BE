package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.CTHD;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CTHDRepo extends JpaRepository<CTHD, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `railwayticketreservationdb`.`cthd` (`ma_hoa_don`, `ma_ve_tau`, `don_gia`) VALUES (?, ?, ?)", nativeQuery = true)
    public int themCTHD(Long maHoaDon, Long maVeTau, double donGia);
    @Query(value = "SELECT * FROM railwayticketreservationdb.cthd WHERE ma_hoa_don = ?", nativeQuery = true)
    public Set<CTHD> getCTHDByHoaDonId(Long hoaDonID);
}
