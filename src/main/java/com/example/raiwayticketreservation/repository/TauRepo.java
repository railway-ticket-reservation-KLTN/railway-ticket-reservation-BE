package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.Tau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TauRepo extends JpaRepository<Tau, Long> {
    @Query(value = "SELECT id FROM railwayticketreservationdb.tau WHERE ten_tau = ?", nativeQuery = true)
    public Long getIdTauByTenTau(String tenTau);
}
