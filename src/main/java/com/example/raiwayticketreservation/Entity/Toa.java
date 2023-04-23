package com.example.raiwayticketreservation.Entity;

import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;
@NamedNativeQuery(name = "Toa.getToasByHanhTrinhIDTauID",
        query = "SELECT t.id as id, c.so_toa as soToa, tr.ten_tau as tenTau, t.mo_ta_toa as moTaToa, t.so_luong_ghe as soLuongGhe, t.ten_toa as tenToa\n" +
                "FROM CTTauToa c, Toa t, Tau tr\n" +
                "WHERE ma_toa = t.id AND ma_tau = tr.id AND c.ma_hanh_trinh = ? AND c.ma_tau = ?",
        resultSetMapping = "Mapping.ToaResponse")
@SqlResultSetMapping(name = "Mapping.ToaResponse",
        classes = @ConstructorResult(targetClass = ToaResponse.class,
                columns = {@ColumnResult(name = "id"),
                        @ColumnResult(name = "soToa"),
                        @ColumnResult(name = "tenTau"),
                        @ColumnResult(name = "moTaToa"),
                        @ColumnResult(name = "soLuongGhe"),
                        @ColumnResult(name = "tenToa")}))
@Entity
@Data
@ToString
@Table(name = "toa")
public class Toa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String tenToa;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String moTaToa;

    private int soLuongGhe;

    @OneToMany(mappedBy = "toa")
    private Set<CTTauToa> ctTauToas;

    @OneToMany(mappedBy = "toa")
    private Set<Ghe> ghes;
}
