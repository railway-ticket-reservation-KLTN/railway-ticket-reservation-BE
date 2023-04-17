package com.example.raiwayticketreservation.Entity;

import com.example.raiwayticketreservation.dtos.GheResponse;
import com.example.raiwayticketreservation.dtos.ToaTheoTauResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@NamedNativeQuery(name = "Ghe.getGheByToaId",
        query = "SELECT id, loai_ghe as loaiGhe, so_ghe as soGhe FROM railwayticketreservationdb.ghe WHERE ma_toa = ?",
        resultSetMapping = "Mapping.GheResponse")
@SqlResultSetMapping(name = "Mapping.GheResponse",
        classes = @ConstructorResult(targetClass = GheResponse.class,
                columns = {@ColumnResult(name = "id"),
                        @ColumnResult(name = "loaiGhe"),
                        @ColumnResult(name = "soGhe")}))
@Entity
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ghe")
public class Ghe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String soGhe;
    private String loaiGhe;
    private int trangThai;

    @OneToMany(mappedBy = "ghe")
    private Set<TrangThaiGhe> trangThaiGhes;

    @ManyToOne
    @JoinColumn(name = "maToa")
    private Toa toa;
}
