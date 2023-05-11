package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "taikhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String tenTaiKhoan;

    @Column(columnDefinition = "VARCHAR(150)")
    private String matKhau;

    @Column(columnDefinition = "VARCHAR(50)")
    private String loaiTK;

    private int trangThai;

    @JsonBackReference("maNhanVien")
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;
}
