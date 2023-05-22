package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NhanVienRepo extends JpaRepository<NhanVien, Long> {

    @Query(value = "SELECT count(id) FROM railwayticketreservationdb.nhanvien " +
            "WHERE ten_nhan_vien = ? AND dia_chi = ? AND sdt = ? AND vi_tri = ?", nativeQuery = true)
    public int kiemTraNhanVienTonTai(String hoTen, String diaChi, String sdt, String viTri);

    @Query(value = "SELECT id FROM railwayticketreservationdb.nhanvien " +
            "WHERE ten_nhan_vien = ? AND dia_chi = ? AND sdt = ? AND vi_tri = ?", nativeQuery = true)
    public Long getIdNhanVien(String hoTen, String diaChi, String sdt, String viTri);
}
