package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.Tau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TauService {
    public Set<Tau> getTauByHanhTrinhID(Long id);
}
