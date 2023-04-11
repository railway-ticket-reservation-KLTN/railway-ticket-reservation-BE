package com.example.raiwayticketreservation.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Entity
@Data
@ToString
@Table(name = "quydinh")
public class QuyDinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tieuDe;
    private String moTa;
    private int trangThai;

    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private NhanVien nhanVien;
}
