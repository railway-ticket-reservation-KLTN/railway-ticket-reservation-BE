package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.dtos.TrangThaiGheResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TrangThaiGheRepo extends JpaRepository <TrangThaiGhe, Long> {
    @Query(value = "SELECT id FROM railwayticketdb.trangthaighe\n" +
            "WHERE ga_di = ? AND ga_den = ? AND ma_ghe = ? AND ma_ve = ? AND trang_thai = ?", nativeQuery = true)
    public Long getIdByTrangThaiGheRequest(String gaDi, String gaDen, Long maGhe, Long maVe, String trangThai);

    @Query(nativeQuery = true)
    public List<TrangThaiGheResponse> getTrangThaiGhesBangMaGheTenTauNgayDiSoToa(Long maGhe, String tenTau, String ngayDi, int soToa);
}
