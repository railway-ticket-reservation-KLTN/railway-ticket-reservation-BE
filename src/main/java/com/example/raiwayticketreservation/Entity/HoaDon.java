package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hoadon")
@ToString
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "maKhachDat")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private KhachDatVe khachDatVe;
    private String ngayLap;
    private String hinhThucThanhToan;
    @Column(columnDefinition = "VARCHAR(40)")
    private String maDatVe;
    @Column(columnDefinition = "VARCHAR(40)")
    private String maDatCho;
    private String tinhTrang;
    private int trangThai;

    @OneToMany(mappedBy = "hoaDon")
    private Set<CTHD> cthd;
}
