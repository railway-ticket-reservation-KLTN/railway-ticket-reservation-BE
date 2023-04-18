package com.example.raiwayticketreservation.Entity;

import com.example.raiwayticketreservation.dtos.ToaTheoTauResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@NamedNativeQuery(name = "Tau.getToaTheoTauByMaHanhTrinhMaTau",
        query = "SELECT d.ma_hanh_trinh as maHanhTrinh, t.ten_tau as tenTau, d.so_toa as soToa, " +
                "c.ten_toa as tenToa, c.mo_ta_toa as moTaToa, c.so_luong_ghe as soLuongGhe" +
                " FROM Tau t, Toa c, CTTauToa d " +
                "WHERE t.id = d.ma_tau AND c.id = d.ma_toa AND d.ma_hanh_trinh = ?",
        resultSetMapping = "Mapping.ToaTheoTauResponse")
@SqlResultSetMapping(name = "Mapping.ToaTheoTauResponse",
        classes = @ConstructorResult(targetClass = ToaTheoTauResponse.class,
                columns = {@ColumnResult(name = "maHanhTrinh"),
                        @ColumnResult(name = "tenTau"),
                        @ColumnResult(name = "soToa"),
                        @ColumnResult(name = "tenToa"),
                        @ColumnResult(name = "moTaToa"),
                        @ColumnResult(name = "soLuongGhe")}))
@Entity
@Data
@ToString
@Table(name = "tau")
public class Tau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenTau;
    private int soLuongToa;

    @OneToMany(mappedBy = "tau")
    @JsonIgnore
    private Set<CTHanhTrinhTau> ctHanhTrinhTaus;

    @OneToMany(mappedBy = "tau")
    @JsonIgnore
    private Set<CTTauToa> ctTauToas;
}
