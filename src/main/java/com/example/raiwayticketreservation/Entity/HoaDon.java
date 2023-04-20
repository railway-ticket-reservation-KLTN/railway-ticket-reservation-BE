package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private KhachDatVe khachDatVe;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date ngayLap;

    @Column(columnDefinition = "VARCHAR(80)")
    private String hinhThucThanhToan;

    @Column(columnDefinition = "VARCHAR(40)")
    private String maDatVe;

    @Column(columnDefinition = "VARCHAR(40)")
    private String maDatCho;

    @Column(columnDefinition = "VARCHAR(80)")
    private String tinhTrang;

    private int trangThai;

    @OneToMany(mappedBy = "hoaDon")
    private Set<CTHD> cthd;
}
