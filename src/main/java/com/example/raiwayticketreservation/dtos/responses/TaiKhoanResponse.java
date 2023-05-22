package com.example.raiwayticketreservation.dtos.responses;

import com.example.raiwayticketreservation.entities.NhanVien;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaiKhoanResponse {
    private Long id;

    private String tenTaiKhoan;

    private String matKhau;

    private String loaiTK;

    private int trangThai;

    private NhanVien nhanVien;
}
