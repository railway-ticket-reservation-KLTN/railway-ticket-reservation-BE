package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.Toa;
import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ToaRepo extends JpaRepository<Toa, Long> {
    @Query(nativeQuery = true)
    public Set<ToaResponse> getToasByHanhTrinhIDTauID (Long hanhTrinhID, Long tauID);
}
