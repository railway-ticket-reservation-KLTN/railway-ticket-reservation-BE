package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.dtos.interfaceDTO.ToaResponseProjection;
import com.example.raiwayticketreservation.entities.Toa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ToaRepo extends JpaRepository<Toa, Long> {
    @Query(value = "SELECT t.id as maToa, c.so_toa as soToa, tr.ten_tau as tenTau, t.mo_ta_toa as moTaToa, t.so_luong_ghe as soLuongGhe, t.ten_toa as tenToa\n" +
            "FROM cttautoa c, toa t, tau tr\n" +
            "WHERE ma_toa = t.id AND ma_tau = tr.id AND c.ma_tau = ? " +
            "ORDER BY soToa",nativeQuery = true)
    public Set<ToaResponseProjection> getToasByTauID (Long tauID);
}
