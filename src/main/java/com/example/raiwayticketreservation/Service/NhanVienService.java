package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.NhanVien;

import java.util.List;


public interface NhanVienService {
    public List<NhanVien> getDanhSachNhanVien();
    public NhanVien themNhanVien(NhanVien nhanVien);

    public boolean kiemTraNhanVienTonTai(NhanVien nhanVien);

    public Long getIdNhanVien(NhanVien nhanVien);
}
