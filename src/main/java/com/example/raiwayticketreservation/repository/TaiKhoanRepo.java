package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.TaiKhoan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaiKhoanRepo extends JpaRepository<TaiKhoan, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE railwayticketreservationdb.taikhoan \n" +
            "SET trang_thai = 0 \n" +
            "WHERE (id = ?)", nativeQuery = true)
    public void xoaTaiKhoan(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE railwayticketreservationdb.taikhoan \n" +
            "SET loaitk = ?, ten_tai_khoan = ?, mat_khau = ?, ma_nhan_vien = ?, trang_thai = ? \n" +
            "WHERE (id = ?)", nativeQuery = true)
    public void capNhatTaiKhoan(String loaiTK, String tenTK, String matKhau, Long maNV, int trangThai, Long id);

    @Query(value = "SELECT count(id) FROM railwayticketreservationdb.taikhoan " +
            "WHERE ma_nhan_vien = ?", nativeQuery = true)
    public int kiemTraTaiKhoanTheoMaNhanVien(Long maNhanVien);

    @Query(value = "SELECT trang_thai FROM railwayticketreservationdb.taikhoan " +
            "WHERE ten_tai_khoan = ?", nativeQuery = true)
    public int getTrangThaiTheoTenTK(String tenTK);

    @Query(value = "SELECT * FROM railwayticketreservationdb.taikhoan\n" +
            "WHERE ten_tai_khoan = ?", nativeQuery = true)
    public Optional<TaiKhoan> timTaiKhoanTheoTenTaiKhoan(String tenTaiKhoan);
}
