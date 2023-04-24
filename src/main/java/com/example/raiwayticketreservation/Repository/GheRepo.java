package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.Ghe;
import com.example.raiwayticketreservation.dtos.responses.DatChoResponse;
import com.example.raiwayticketreservation.dtos.responses.GheResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
public interface GheRepo extends JpaRepository<Ghe, Long> {
    @Query(nativeQuery = true)
    public Set<GheResponse> getGheByToaId(Long id);
    @Query(value = "INSERT INTO `railwayticketreservationdb`.`trang_thai_ghe` (`ga_di`, `ga_den`, `ma_ghe`, `ma_ve`, `trang_thai`) " +
            "VALUES (?, ?, ?, ?, ?)", nativeQuery = true)
    public DatChoResponse datCho(String gaDi, String gaDen, Long maGhe, Long maVe, String trangThai);

    @Query(value = "SELECT * FROM railwayticketreservationdb.ghe\n" +
            "WHERE ma_toa = ?", nativeQuery = true)
    public Set<Ghe> getDsGheTheoMaToa(Long maToa);
}
