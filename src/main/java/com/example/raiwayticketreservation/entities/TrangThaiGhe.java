package com.example.raiwayticketreservation.entities;

import com.example.raiwayticketreservation.dtos.responses.TrangThaiGheResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@NamedNativeQuery(name = "TrangThaiGhe.getTrangThaiGhesBangMaGheTenTauNgayDiSoToa",
        query = "SELECT id, ga_den as gaden, ga_di as gaDi, " +
                "trang_thai as trangThai, ma_ghe as maGhe, " +
                "ngay_di as ngayDi, gio_di as gioDi, gio_den as gioDen, " +
                "ten_tau as tenTau, so_toa as soToa, thoi_han_giu_ghe as thoiHanGiuGhe" +
                " FROM TrangThaiGhe t" +
                " WHERE ma_ghe = ? AND ten_tau = ? AND ngay_di = ? AND so_toa = ?",
        resultSetMapping = "Mapping.TrangThaiGheResponse")
@SqlResultSetMapping(name = "Mapping.TrangThaiGheResponse",
        classes = @ConstructorResult(targetClass = TrangThaiGheResponse.class,
                columns = {@ColumnResult(name = "id"),
                        @ColumnResult(name = "gaDen"),
                        @ColumnResult(name = "gaDi"),
                        @ColumnResult(name = "trangThai"),
                        @ColumnResult(name = "maGhe"),
                        @ColumnResult(name = "ngayDi"),
                        @ColumnResult(name = "gioDi"),
                        @ColumnResult(name = "gioDen"),
                        @ColumnResult(name = "tenTau"),
                        @ColumnResult(name = "soToa"),
                        @ColumnResult(name = "thoiHanGiuGhe")}))
@Entity
@Data
@ToString
@Table(name = "trangthaighe")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrangThaiGhe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maGhe")
    private Ghe ghe;

    @Column(columnDefinition = "NVARCHAR(80)")
    private String gaDi;

    @Column(columnDefinition = "NVARCHAR(80)")
    private String gaDen;

    @Column(columnDefinition = "Date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date ngayDi;

    @Column(columnDefinition = "TIME")
    @JsonFormat(pattern="HH:mm:ss")
    private Time gioDi;

    @Column(columnDefinition = "TIME")
    @JsonFormat(pattern="HH:mm:ss")
    private Time gioDen;

    @Column(columnDefinition = "VARCHAR(10)")
    private String tenTau;

    private int soToa;

    @Column(columnDefinition = "VARCHAR(50)")
    private String trangThai;

    @Column(columnDefinition = "VARCHAR(20)")
    private String maVeTau;

    private Timestamp thoiHanGiuGhe;
}
