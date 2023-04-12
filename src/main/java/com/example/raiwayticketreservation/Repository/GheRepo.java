package com.example.raiwayticketreservation.Repository;

import com.example.raiwayticketreservation.Entity.Ghe;
import com.example.raiwayticketreservation.dtos.GheResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface GheRepo extends JpaRepository<Ghe, Long> {
    @Query(nativeQuery = true)
    public Set<GheResponse> getGheByToaId(Long id);
}
