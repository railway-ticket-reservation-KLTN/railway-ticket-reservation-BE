package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "taikhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenTaiKhoan;
    private String matKhau;
    private String loaiTK;
    private int trangThai;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;
}
