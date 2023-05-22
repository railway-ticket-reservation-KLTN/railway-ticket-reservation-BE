package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.Toa;
import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ToaRepo extends JpaRepository<Toa, Long> {
    @Query(nativeQuery = true)
    public Set<ToaResponse> getToasByTauID (Long tauID);
}