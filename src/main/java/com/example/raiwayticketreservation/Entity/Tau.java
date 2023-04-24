package com.example.raiwayticketreservation.Entity;

import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@NamedNativeQuery(name = "Tau.getToaTheoTauByMaHanhTrinhMaTau",
        query = "SELECT c.id as maToa, t.ten_tau as tenTau, d.so_toa as soToa,\n" +
                "c.ten_toa as tenToa, c.mo_ta_toa as moTaToa, c.so_luong_ghe as soLuongGhe\n" +
                "FROM Tau t, Toa c, CTTauToa d \n" +
                "WHERE t.id = d.ma_tau AND c.id = d.ma_toa AND d.ma_hanh_trinh = ?",
        resultSetMapping = "Mapping.ToaTauResponse")
@SqlResultSetMapping(name = "Mapping.ToaTauResponse",
        classes = @ConstructorResult(targetClass = ToaResponse.class,
                columns = {@ColumnResult(name = "maToa"),
                        @ColumnResult(name = "soToa"),
                        @ColumnResult(name = "tenTau"),
                        @ColumnResult(name = "moTaToa"),
                        @ColumnResult(name = "soLuongGhe"),
                        @ColumnResult(name = "tenToa")}))
@Entity
@Data
@ToString
@Table(name = "tau")
public class Tau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(10)")
    private String tenTau;

    private int soLuongToa;

    @OneToMany(mappedBy = "tau")
    @JsonIgnore
    private Set<CTHanhTrinhTau> ctHanhTrinhTaus;

    @OneToMany(mappedBy = "tau")
    @JsonIgnore
    private Set<CTTauToa> ctTauToas;
}
