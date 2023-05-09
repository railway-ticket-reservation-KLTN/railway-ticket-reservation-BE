package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.NhanVien;


public interface NhanVienService {
    public NhanVien themNhanVien(NhanVien nhanVien);

    public boolean kiemTraNhanVienTonTai(NhanVien nhanVien);

    public Long getIdNhanVien(NhanVien nhanVien);
}
