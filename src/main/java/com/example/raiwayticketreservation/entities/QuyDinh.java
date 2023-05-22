package com.example.raiwayticketreservation.entities;

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

    @Column(columnDefinition = "NVARCHAR(100)")
    private String tieuDe;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    private int trangThai;

    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private NhanVien nhanVien;
}
