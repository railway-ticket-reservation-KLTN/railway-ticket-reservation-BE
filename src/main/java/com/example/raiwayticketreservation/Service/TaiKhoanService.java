package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.TaiKhoan;
import com.example.raiwayticketreservation.dtos.responses.TaiKhoanResponse;

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
