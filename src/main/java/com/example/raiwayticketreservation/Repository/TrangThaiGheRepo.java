package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.dtos.responses.TrangThaiGheResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TrangThaiGheRepo extends JpaRepository <TrangThaiGhe, Long> {
    @Query(value = "SELECT * FROM railwayticketreservationdb.trangthaighe\n" +
            "WHERE ga_di = ? AND ga_den = ? AND ngay_di = ? " +
            "AND so_toa = ? AND trang_thai = ?", nativeQuery = true)
    public List<TrangThaiGhe> getTrangThaiGheByThongTinHanhTrinh(String gaDi, String gaDen, String ngayDi, int soToa, String trangThai);


    @Query(value = "SELECT id FROM railwayticketreservationdb.trangthaighe\n" +
            "WHERE ga_di = ? AND ga_den = ? AND ngay_di = ? " +
            "AND ma_ghe = ? AND so_toa = ? AND trang_thai = ?", nativeQuery = true)
    public Long getIdByTrangThaiGheRequest(String gaDi, String gaDen, String ngayDi, Long maGhe, int soToa, String trangThai);

    @Query(nativeQuery = true)
    public List<TrangThaiGheResponse> getTrangThaiGhesBangMaGheTenTauNgayDiSoToa(Long maGhe, String tenTau, String ngayDi, int soToa);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `railwayticketreservationdb`.`trangthaighe` \n" +
            "SET ma_ve_tau = ?, trang_thai = ?  \n" +
            "WHERE (id = ?);", nativeQuery = true)
    public void updateTrangThaiGheByID(String maVeTau, String trangThai, Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `railwayticketreservationdb`.`trangthaighe` \n" +
            "SET thoi_han_giu_ghe = null \n" +
            "WHERE (ma_ve_tau = ?);", nativeQuery = true)
    public void updateThoiHanGiuGheByMaVe(String maVeTau);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM railwayticketreservationdb.trangthaighe\n" +
            "WHERE  ma_ve_tau = ?", nativeQuery = true)
    public void xoaTrangThaiGheByMaVe(String maVe);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM railwayticketreservationdb.trangthaighe\n" +
            "WHERE  trang_thai = 'DAT_CHO' AND  TIMESTAMPDIFF(HOUR, thoi_han_giu_ghe, NOW()) >= 0 " +
            "OR trang_thai = 'DA_MUA' AND TIMESTAMPDIFF(HOUR, thoi_han_giu_ghe, NOW()) >= 0", nativeQuery = true)
    public void xoaTrangThaiGheDaHetHan();
}
