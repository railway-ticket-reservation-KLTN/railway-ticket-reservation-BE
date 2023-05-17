package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HanhTrinhRepo extends JpaRepository<HanhTrinh, Long> {
    @Query(value = "SELECT * FROM railwayticketreservationdb.hanhtrinh " +
            "WHERE ga_di = ? AND ga_den = ? AND ngay_di = ?",
            nativeQuery = true)
    public List<HanhTrinh> getHanhTrinhByGaDiGaDenNgayDiNgayDen(String gaDi, String gaDen, String ngayDi);
    @Query(value = "SELECT id from railwayticketreservationdb.hanhtrinh \n" +
            "WHERE ga_di = ? AND ga_den = ? AND ngay_di = ? " +
            "AND ngay_den = ? AND gio_di = ? AND gio_den = ? AND ma_tau = ?", nativeQuery = true)
    public Long getIdHanhTrinh(String gaDi, String gaDen, String ngayDi, String ngayDen, String gioDi, String gioDen, Long maTau);

    @Query(value = "SELECT * FROM railwayticketreservationdb.hanhtrinh WHERE id = ?", nativeQuery = true)
    public HanhTrinh getHanhTrinhByHanhTrinhID(Long hanhTrinhID);

    @Query(value = "SELECT count(id) FROM railwayticketreservationdb.hanhtrinh " +
            "WHERE ga_di = ? AND ga_den = ? AND ngay_di = ? " +
            "AND ngay_den = ? AND gio_di = ? AND gio_den = ? AND ma_tau = ?", nativeQuery = true)
    public int kiemTraHanhTrinhTonTai(String gaDi, String gaDen, String ngayDi,
                                      String ngayDen, String gioDi, String gioDen, Long idTau);
    @Modifying
    @Transactional
    @Query(value = "UPDATE railwayticketreservationdb.hanhtrinh\n" +
            "SET ga_di = ?, ga_den = ?, gio_di = ?, gio_den = ?,\n" +
            "ngay_di = ?, ngay_den = ?, gia_ve = ?, ma_tau = ?, trang_thai = 1\n" +
            "WHERE id = ?", nativeQuery = true)
    public void capNhatHanhTrinh(String gaDi, String gaDen, String gioDi, String gioDen,
                                      String ngayDi, String ngayDen, String giaVe, Long maTau, Long id);
}
