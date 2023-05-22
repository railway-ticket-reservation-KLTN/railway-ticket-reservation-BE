package com.example.raiwayticketreservation.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

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
    @JsonIgnore
    private KhachDatVe khachDatVe;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date ngayLap;

    @Column(columnDefinition = "VARCHAR(80)")
    private String hinhThucThanhToan;

    @Column(columnDefinition = "VARCHAR(20)")
    private String maDatVe;

    @Column(columnDefinition = "VARCHAR(80)")
    private String tinhTrang;

    private int trangThai;

    @OneToMany(mappedBy = "hoaDon")
    private Set<CTHD> cthd;
}
