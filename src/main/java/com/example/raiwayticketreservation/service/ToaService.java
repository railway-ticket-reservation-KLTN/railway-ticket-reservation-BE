package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.dtos.responses.ToaResponse;

import java.util.Set;

public interface ToaService {

    public Set<ToaResponse> getToasByTauID(Long tauID);
}
