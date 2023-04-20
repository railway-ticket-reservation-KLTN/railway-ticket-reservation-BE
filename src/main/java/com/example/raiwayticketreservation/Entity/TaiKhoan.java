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

    @Column(columnDefinition = "VARCHAR(50)")
    private String tenTaiKhoan;

    @Column(columnDefinition = "VARCHAR(50)")
    private String matKhau;

    @Column(columnDefinition = "VARCHAR(50)")
    private String loaiTK;

    private int trangThai;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;
}
