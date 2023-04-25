package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.Tau;
import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Set;

public interface TauRepo extends JpaRepository<Tau, Long> {
    @Query(value = "SELECT * FROM railwayticketreservationdb.tau " +
            "WHERE id = ?", nativeQuery = true)
    public Tau getTau(Long id);

    @Query(nativeQuery = true)
    public ArrayList<ToaResponse> getToaTheoTauByMaHanhTrinhMaTau(Long hanhTrinhID, Long tenTau);
}
