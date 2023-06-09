package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.Ghe;
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
            "WHERE ma_toa = ? AND ma_tau= ? AND toa_so = ?", nativeQuery = true)
    public Set<Ghe> getDsGheTheoMaToaSoToa(Long maToa, Long maTau, int soToa);

    @Query(value = "SELECT so_ghe FROM railwayticketreservationdb.ghe\n" +
            "WHERE id = ?", nativeQuery = true)
    public int getSoGheByGheID(Long gheId);
}
