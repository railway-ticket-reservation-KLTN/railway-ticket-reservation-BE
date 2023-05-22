package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.TaiKhoan;

import java.util.List;

public interface TaiKhoanService {

    public List<TaiKhoan> getDanhSachTaiKhoan();
    public TaiKhoan themTaiKhoan(TaiKhoan taiKhoan);

    public boolean xoaTaiKhoan(TaiKhoan taiKhoan);

    public boolean capNhatTaiKhoan(TaiKhoan taiKhoan);

    public boolean kiemTraTaiKhoanNhanVienTonTai(TaiKhoan taiKhoan);

    public boolean kiemTraTaiKhoanTheoMa(TaiKhoan taiKhoan);

    public boolean kiemTraTaiKhoanConHoatDong(TaiKhoan taiKhoan);
}
