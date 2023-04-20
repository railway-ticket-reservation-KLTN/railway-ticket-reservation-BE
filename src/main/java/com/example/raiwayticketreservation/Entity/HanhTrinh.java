package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hanhtrinh")
public class HanhTrinh implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(80)")
    private String gaDi;

    @Column(columnDefinition = "NVARCHAR(80)")
    private String gaDen;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date ngayDi;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date ngayDen;

    @Column(columnDefinition = "TIME")
    @JsonFormat(pattern="HH:mm:ss")
    private Time gioDi;

    @Column(columnDefinition = "TIME")
    @JsonFormat(pattern="HH:mm:ss")
    private Time gioDen;

    private int trangThai;

    private double giaVe;

    @OneToMany(mappedBy = "hanhTrinh")
    @JsonIgnore
    private Set<VeTau> veTaus;

    @OneToMany(mappedBy = "hanhTrinh")
    @JsonIgnore
    private Set<CTHanhTrinhTau> ctHanhTrinhTaus;
}
