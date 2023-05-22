package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.Ghe;
import com.example.raiwayticketreservation.dtos.requests.GheRequest;
import com.example.raiwayticketreservation.dtos.responses.GheResponse;
import com.example.raiwayticketreservation.dtos.requests.TrangThaiGheRequest;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Set;

public interface GheService {
    public Set<GheResponse> getGheTheoToaID(Long id);

    public ResponseEntity datChoTam(TrangThaiGheRequest trangThaiGheRequest) throws ParseException;

    public boolean xoaDatChoTam(TrangThaiGheRequest trangThaiGheRequest);

    public Set<Ghe> getGhesTheoMaToa(GheRequest gheRequest);

    public int getSoGheTheoMaGhe(Long maGhe);

    public Set<Ghe> getDsGheTheoMaToaSoToa(Long maToa, Long maTau, int soToa);
}
