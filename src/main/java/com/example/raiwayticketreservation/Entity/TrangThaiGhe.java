package com.example.raiwayticketreservation.Entity;

import com.example.raiwayticketreservation.dtos.responses.TrangThaiGheResponse;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

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

    private String gaDi;
    private String gaDen;
    private String ngayDi;
    private String gioDi;
    private String gioDen;
    private String tenTau;
    private int soToa;
    private String trangThai;
    private String maVeTau;
    private Timestamp thoiHanGiuGhe;
}