package com.example.raiwayticketreservation.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
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
    private LocalDate ngayDi;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate ngayDen;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm:ss")
    private Time gioDi;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm:ss")
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
