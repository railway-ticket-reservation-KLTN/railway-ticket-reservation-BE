package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.dtos.GheResponse;

import java.util.Set;

public interface GheService {
    public Set<GheResponse> getGheTheoToaID(Long id);
}
