package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.NhanVien;

import java.util.List;


public interface NhanVienService {
    public List<NhanVien> getDanhSachNhanVien();
    public NhanVien themNhanVien(NhanVien nhanVien);

    public boolean kiemTraNhanVienTonTai(NhanVien nhanVien);

    public Long getIdNhanVien(NhanVien nhanVien);
}
