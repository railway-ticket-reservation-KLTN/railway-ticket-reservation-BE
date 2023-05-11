package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;
@Entity
@Data
@ToString
@Table(name = "nhanvien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(150)")
    private String tenNhanVien;

    @Column(columnDefinition = "NVARCHAR(150)")
    private String diaChi;

    @Column(columnDefinition = "VARCHAR(15)")
    private String sdt;

    @Column(columnDefinition = "VARCHAR(50)")
    private String viTri;

    private int trangThai;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<QuyDinh> quyDinhs;

    @JsonManagedReference(value = "nhanVien")
    @OneToOne(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    @JsonIgnore
    private TaiKhoan taiKhoan;

    @ManyToOne
    @JoinColumn(name = "maQuanLy")
    private NhanVien quanLy;
}
