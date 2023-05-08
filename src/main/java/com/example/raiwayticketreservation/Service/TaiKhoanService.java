package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.NhanVien;
import com.example.raiwayticketreservation.Entity.TaiKhoan;

public interface TaiKhoanService {
    public TaiKhoan themTaiKhoan(TaiKhoan taiKhoan);

    public boolean xoaTaiKhoan(TaiKhoan taiKhoan);

    public boolean capNhatTaiKhoan(TaiKhoan taiKhoan);
}
