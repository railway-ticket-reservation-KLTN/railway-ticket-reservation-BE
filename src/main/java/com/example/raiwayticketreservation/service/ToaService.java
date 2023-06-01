package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.dtos.interfaceDTO.ToaResponseProjection;
import com.example.raiwayticketreservation.dtos.responses.ToaResponse;

import java.util.Set;

public interface ToaService {

    public Set<ToaResponseProjection> getToasByTauID(Long tauID);
}
